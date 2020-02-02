package men.brakh.enrollment.domain.educationDocument.service;

import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.enrollment.application.service.CRUDEntityService;

import java.util.List;

public interface EducationDocumentService extends CRUDEntityService<EducationDocumentDto,
        EducationDocumentCreateRequest,
        EducationDocumentUpdateRequest,
        Integer> {
    List<EducationDocumentDto> getByEnrolleeId(final Integer id);
}
