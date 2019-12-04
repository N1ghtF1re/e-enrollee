package men.brakh.enrollment.model.employee.credentials;

import men.brakh.enrollment.repository.CRUDRepository;

import java.util.List;

public interface EmployeeCredentialsRepository extends CRUDRepository<EmployeeCredentials, Integer> {
  List<EmployeeCredentials> findByEmployeeId(Integer employeeId);
}
