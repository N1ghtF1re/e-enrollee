package men.brakh.enrollment.model.employee.service;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.mapping.mapper.DtoMapper;
import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.employee.Employee;
import men.brakh.enrollment.model.employee.Role;
import men.brakh.enrollment.model.employee.credentials.EmployeeCredentials;
import men.brakh.enrollment.model.employee.credentials.EmployeeCredentialsRepository;
import men.brakh.enrollment.model.employee.dto.EmployeeDto;
import men.brakh.enrollment.model.employee.dto.EmployeeRegistrationRequest;
import men.brakh.enrollment.model.employee.dto.EmployeeUpdateReuqest;
import men.brakh.enrollment.model.employee.repository.EmployeeRepository;
import men.brakh.enrollment.service.AbstractCRUDEntityService;

import javax.validation.Validator;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;

public class EmployeeServiceImpl extends AbstractCRUDEntityService<
    Employee,
    EmployeeDto,
    EmployeeRegistrationRequest,
    EmployeeUpdateReuqest,
    Integer
    > implements EmployeeService{

  private final EmployeeCredentialsRepository employeeCredentialsRepository;
  private final MessageDigest messageDigest;
  private final EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(final EmployeeRepository crudRepository,
                             final DtoMapper<EmployeeDto, Employee> dtoMapper,
                             final EntityPresenter<Employee, EmployeeDto> entityPresenter,
                             final Validator validator,
                             final EmployeeCredentialsRepository employeeCredentialsRepository,
                             final MessageDigest messageDigest) {
    super(crudRepository, dtoMapper, entityPresenter, validator);
    this.employeeCredentialsRepository = employeeCredentialsRepository;
    this.messageDigest = messageDigest;
    this.employeeRepository = crudRepository;
  }

  @Override
  public EmployeeDto create(final EmployeeRegistrationRequest createRequest) throws BadRequestException {
    throwIfUsernameAlreadyExists(createRequest.getUsername());

    final SecureRandom random = new SecureRandom();
    final byte[] salt = new byte[64];
    random.nextBytes(salt);

    final byte[] hashedPassword;

    synchronized (messageDigest) {
      messageDigest.update(salt);
      hashedPassword = messageDigest.digest(createRequest.getPassword().getBytes(StandardCharsets.UTF_8));
    }

    createRequest.setRole(Role.SECRETARY.toString());

    final EmployeeDto employeeDto = createTemplate.save(createRequest, EmployeeDto.class);

    final EmployeeCredentials employeeCredentials = EmployeeCredentials.builder()
        .employeeId(employeeDto.getId())
        .password(hashedPassword)
        .salt(salt)
        .build();

    employeeCredentialsRepository.create(employeeCredentials);

    return employeeDto;
  }

  private void throwIfUsernameAlreadyExists(final String username) throws BadRequestException {
    if (!employeeRepository.findByUsername(username).isEmpty()) {
      throw new BadRequestException("Username must be unique");
    }
  }

  @Override
  public void delete(final Integer id) throws BadRequestException {
    deleteTemplate.delete(id);

  }

  @Override
  public EmployeeDto getById(final Integer id) throws BadRequestException {
    return getTemplate.getById(id, EmployeeDto.class);
  }

  @Override
  public List<EmployeeDto> getAll() {
    return getTemplate.getAll(EmployeeDto.class);
  }

  @Override
  public EmployeeDto update(final Integer id, final EmployeeUpdateReuqest updateRequest) throws BadRequestException {
    return updateTemplate.update(id, updateRequest, EmployeeDto.class);
  }

  @Override
  public boolean isCredentialsCorrect(final String username, final String password) {
    final List<Employee> employees = employeeRepository.findByUsername(username);
    if (employees.size() != 1) {
      return false;
    }

    final Employee employee = employees.get(0);
    final EmployeeCredentials credentials = employeeCredentialsRepository.findByEmployeeId(employee.getId()).get(0);

    final byte[] hashedPassword;

    synchronized (messageDigest) {
      messageDigest.update(credentials.getSalt());

      hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
    }



    return MessageDigest.isEqual(hashedPassword, credentials.getPassword());
  }

  @Override
  public Integer getIdByUsername(final String username) {
    final Employee employee = employeeRepository.findByUsername(username).get(0);
    return employee.getId();
  }
}
