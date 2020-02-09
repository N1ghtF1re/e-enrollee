package men.brakh.enrollment.domain.employee.service;

import men.brakh.enrollment.domain.employee.dto.EmployeeDto;
import men.brakh.enrollment.domain.employee.dto.EmployeeRegistrationRequest;
import men.brakh.enrollment.domain.employee.dto.EmployeeUpdateRequest;
import men.brakh.enrollment.application.service.CRUDEntityService;

public interface EmployeeService extends CRUDEntityService<
    EmployeeDto,
    EmployeeRegistrationRequest,
    EmployeeUpdateRequest,
    Integer> {

  Integer getIdByUsername(String username);
}
