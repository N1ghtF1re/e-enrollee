package men.brakh.enrollment.model.universityApplication.service;

import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationCreateRequest;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationDto;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationUpdateRequest;
import men.brakh.enrollment.service.CRUDEntityService;

public interface UniversityApplicationService extends CRUDEntityService<
        UniversityApplicationDto,
        UniversityApplicationCreateRequest,
        UniversityApplicationUpdateRequest,
        Integer
        > {
}
