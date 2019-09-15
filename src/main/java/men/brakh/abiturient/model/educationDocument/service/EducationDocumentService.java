package men.brakh.abiturient.model.educationDocument.service;

import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.abiturient.service.CRUDEntityService;

public interface EducationDocumentService extends CRUDEntityService<EducationDocumentDto,
        EducationDocumentCreateRequest,
        EducationDocumentUpdateRequest,
        Integer> {
}
