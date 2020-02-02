package men.brakh.enrollment.domain.employee.service;

import men.brakh.enrollment.domain.employee.dto.EmployeeDto;
import men.brakh.enrollment.domain.employee.dto.EmployeeRegistrationRequest;
import men.brakh.enrollment.domain.employee.dto.EmployeeUpdateReuqest;
import men.brakh.enrollment.application.service.CRUDEntityService;

public interface EmployeeService extends CRUDEntityService<
    EmployeeDto,
    EmployeeRegistrationRequest,
    EmployeeUpdateReuqest,
    Integer> {

  Integer getIdByUsername(String username);
}
