package men.brakh.abiturient.model.educationDocument.service;

import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.abiturient.service.CRUDEntityService;

import java.util.List;

public interface EducationDocumentService extends CRUDEntityService<EducationDocumentDto,
        EducationDocumentCreateRequest,
        EducationDocumentUpdateRequest,
        Integer> {
    List<EducationDocumentDto> getByAbiturientId(final Integer id);
}
