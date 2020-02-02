package men.brakh.enrollment.domain.enrollee.service;

import men.brakh.enrollment.domain.enrollee.dto.EnrolleeCreateRequest;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeUpdateRequest;
import men.brakh.enrollment.application.service.CRUDEntityService;

public interface EnrolleeService extends CRUDEntityService<
        EnrolleeDto,
        EnrolleeCreateRequest,
        EnrolleeUpdateRequest,
        Integer> {
}
