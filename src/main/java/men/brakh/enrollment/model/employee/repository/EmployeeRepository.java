package men.brakh.enrollment.model.employee.repository;

import men.brakh.enrollment.model.employee.Employee;
import men.brakh.enrollment.repository.CRUDRepository;

import java.util.List;

public interface EmployeeRepository extends CRUDRepository<Employee, Integer> {
  List<Employee> findByUsername(final String username);
}
