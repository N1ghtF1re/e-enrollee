package men.brakh.enrollment.security.credentials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCredentialsRepository extends JpaRepository<
    EmployeeCredentials, Integer> {
  EmployeeCredentials findOneByEmployeeId(Integer employeeId);
}
