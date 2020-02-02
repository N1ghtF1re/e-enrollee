package men.brakh.enrollment.domain.employee.service;

import java.util.List;
import javax.validation.Validator;
import men.brakh.enrollment.application.mapping.mapper.DtoMapper;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.application.service.AbstractCRUDEntityService;
import men.brakh.enrollment.domain.employee.Employee;
import men.brakh.enrollment.domain.employee.Role;
import men.brakh.enrollment.domain.employee.dto.EmployeeDto;
import men.brakh.enrollment.domain.employee.dto.EmployeeRegistrationRequest;
import men.brakh.enrollment.domain.employee.dto.EmployeeUpdateReuqest;
import men.brakh.enrollment.domain.employee.repository.EmployeeRepository;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.security.credentials.EmployeeCredentials;
import men.brakh.enrollment.security.credentials.EmployeeCredentialsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeServiceImpl extends AbstractCRUDEntityService<
    Employee,
    EmployeeDto,
    EmployeeRegistrationRequest,
    EmployeeUpdateReuqest,
    Integer
    > implements EmployeeService{

  private final EmployeeCredentialsRepository employeeCredentialsRepository;
  private final EmployeeRepository employeeRepository;
  private final PasswordEncoder passwordEncoder;

  public EmployeeServiceImpl(final EmployeeRepository crudRepository,
                             final DtoMapper<EmployeeDto, Employee> dtoMapper,
                             final EntityPresenter<Employee, EmployeeDto> entityPresenter,
                             final Validator validator,
                             final EmployeeCredentialsRepository employeeCredentialsRepository,
                             final PasswordEncoder passwordEncoder) {
    super(crudRepository, dtoMapper, entityPresenter, validator);
    this.employeeCredentialsRepository = employeeCredentialsRepository;
    this.employeeRepository = crudRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @PostMapping("/registration")
  @Transactional(rollbackFor = Exception.class)
  public @ResponseBody EmployeeDto create(
      @RequestBody final EmployeeRegistrationRequest createRequest
  ) throws BadRequestException {
    throwIfUsernameAlreadyExists(createRequest.getUsername());

    createRequest.setRole(Role.SECRETARY.toString());

    final EmployeeDto employeeDto = createTemplate.save(createRequest, EmployeeDto.class);

    final EmployeeCredentials employeeCredentials = EmployeeCredentials.builder()
        .employeeId(employeeDto.getId())
        .password(passwordEncoder.encode(createRequest.getPassword()))
        .build();

    employeeCredentialsRepository.save(employeeCredentials);

    return employeeDto;
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
  public Integer getIdByUsername(final String username) {
    final Employee employee = employeeRepository.findOneByUsername(username);
    return employee.getId();
  }

  private void throwIfUsernameAlreadyExists(final String username) throws BadRequestException {
    if (employeeRepository.findOneByUsername(username) != null) {
      throw new BadRequestException("Username must be unique");
    }
  }
}
