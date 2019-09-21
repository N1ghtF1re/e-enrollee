package men.brakh.abiturient.model.educationDocument.repository;

import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.repository.CRUDRepository;

import java.util.List;

public interface EducationDocumentRepository extends CRUDRepository<EducationDocument, Integer> {
    List<EducationDocument> findByAbiturientId(final Integer abiturientId);
}
