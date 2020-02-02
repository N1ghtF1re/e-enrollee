package men.brakh.enrollment.domain.employee.repository;

import men.brakh.enrollment.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
  Employee findOneByUsername(final String username);
}
