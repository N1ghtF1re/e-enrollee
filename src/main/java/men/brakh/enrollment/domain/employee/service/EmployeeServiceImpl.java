package men.brakh.enrollment.domain.employee.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Validator;
import men.brakh.enrollment.application.mapping.mapper.DtoMapper;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.application.service.AbstractCRUDEntityService;
import men.brakh.enrollment.domain.employee.Employee;
import men.brakh.enrollment.domain.employee.Role;
import men.brakh.enrollment.domain.employee.dto.EmployeeDto;
import men.brakh.enrollment.domain.employee.dto.EmployeeRegistrationRequest;
import men.brakh.enrollment.domain.employee.dto.EmployeeUpdateRequest;
import men.brakh.enrollment.domain.employee.repository.EmployeeRepository;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.exception.ResourceNotFoundException;
import men.brakh.enrollment.security.authentication.credentials.EmployeeCredentials;
import men.brakh.enrollment.security.authentication.credentials.EmployeeCredentialsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeServiceImpl extends AbstractCRUDEntityService<
    Employee,
    EmployeeDto,
    EmployeeRegistrationRequest,
    EmployeeUpdateRequest,
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
  @PostMapping("/api/v1/registration")
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

  @GetMapping("api/v1/me/roles")
  public List<String> getMyPermissions() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ((User) auth.getPrincipal()).getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }


  @Override
  public void delete(final Integer id) throws BadRequestException, ResourceNotFoundException {
    deleteTemplate.delete(id);

  }

  @Override
  public EmployeeDto getById(final Integer id) throws ResourceNotFoundException {
    return getTemplate.getById(id, EmployeeDto.class);
  }

  @Override
  public List<EmployeeDto> getAll() {
    return getTemplate.getAll(EmployeeDto.class);
  }

  @Override
  public EmployeeDto update(final Integer id, final EmployeeUpdateRequest updateRequest) throws BadRequestException, ResourceNotFoundException {
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
