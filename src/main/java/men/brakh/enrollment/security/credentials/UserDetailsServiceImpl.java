package men.brakh.enrollment.security.credentials;

import men.brakh.enrollment.domain.employee.Employee;
import men.brakh.enrollment.domain.employee.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final EmployeeRepository employeeRepository;
  private final EmployeeCredentialsRepository credentialsRepository;

  public UserDetailsServiceImpl(final EmployeeRepository employeeRepository,
                                final EmployeeCredentialsRepository credentialsRepository) {
    this.employeeRepository = employeeRepository;
    this.credentialsRepository = credentialsRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final Employee employee = employeeRepository.findOneByUsername(username);
    final EmployeeCredentials credentials = credentialsRepository.findOneByEmployeeId(employee.getId());

    return User.builder()
        .password(credentials.getPassword())
        .username(employee.getUsername())
        .roles(employee.getRole().toString())
        .build();
  }
}
