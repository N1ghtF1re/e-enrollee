package men.brakh.enrollment.model.educationDocument.repository;

import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.repository.CRUDRepository;

import java.util.List;

public interface EducationDocumentRepository extends CRUDRepository<EducationDocument, Integer> {
    List<EducationDocument> findByEnrolleeId(final Integer enrolleeId);
}
