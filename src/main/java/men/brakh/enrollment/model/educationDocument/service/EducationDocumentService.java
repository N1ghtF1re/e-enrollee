package men.brakh.enrollment.model.educationDocument.service;

import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.enrollment.service.CRUDEntityService;

import java.util.List;

public interface EducationDocumentService extends CRUDEntityService<EducationDocumentDto,
        EducationDocumentCreateRequest,
        EducationDocumentUpdateRequest,
        Integer> {
    List<EducationDocumentDto> getByEnrolleeId(final Integer id);
}
