package men.brakh.enrollment.domain.universityApplication.service;

import men.brakh.enrollment.domain.universityApplication.dto.UniversityApplicationCreateRequest;
import men.brakh.enrollment.domain.universityApplication.dto.UniversityApplicationDto;
import men.brakh.enrollment.domain.universityApplication.dto.UniversityApplicationUpdateRequest;
import men.brakh.enrollment.application.service.CRUDEntityService;

public interface UniversityApplicationService extends CRUDEntityService<
        UniversityApplicationDto,
        UniversityApplicationCreateRequest,
        UniversityApplicationUpdateRequest,
        Integer
        > {
}
