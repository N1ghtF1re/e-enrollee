package men.brakh.enrollment.model.enrollee.service;

import men.brakh.enrollment.model.enrollee.dto.EnrolleeCreateRequest;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeUpdateRequest;
import men.brakh.enrollment.service.CRUDEntityService;

public interface EnrolleeService extends CRUDEntityService<
        EnrolleeDto,
        EnrolleeCreateRequest,
        EnrolleeUpdateRequest,
        Integer> {
}
