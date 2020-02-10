package men.brakh.enrollment.domain.educationDocument.service;

import men.brakh.enrollment.application.search.SearchRequest;
import men.brakh.enrollment.application.search.SearchResponse;
import men.brakh.enrollment.application.service.CRUDEntityService;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.enrollment.exception.BadRequestException;

public interface EducationDocumentService extends CRUDEntityService<EducationDocumentDto,
        EducationDocumentCreateRequest,
        EducationDocumentUpdateRequest,
        Integer> {
    SearchResponse<EducationDocumentDto> search(final SearchRequest searchRequest) throws BadRequestException;
}
