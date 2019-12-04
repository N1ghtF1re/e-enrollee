package men.brakh.enrollment.model.employee.service;

import men.brakh.enrollment.model.employee.dto.EmployeeDto;
import men.brakh.enrollment.model.employee.dto.EmployeeRegistrationRequest;
import men.brakh.enrollment.model.employee.dto.EmployeeUpdateReuqest;
import men.brakh.enrollment.service.CRUDEntityService;

public interface EmployeeService extends CRUDEntityService<
    EmployeeDto,
    EmployeeRegistrationRequest,
    EmployeeUpdateReuqest,
    Integer> {

  boolean isCredentialsCorrect(String username, String password);
  Integer getIdByUsername(String username);
}
