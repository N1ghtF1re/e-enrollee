package men.brakh.enrollment.domain.educationDocument.repository;

import java.util.List;
import men.brakh.enrollment.domain.educationDocument.EducationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationDocumentRepository extends JpaRepository<EducationDocument, Integer> {
    List<EducationDocument> findByEnrolleeId(final Integer enrolleeId);
}
