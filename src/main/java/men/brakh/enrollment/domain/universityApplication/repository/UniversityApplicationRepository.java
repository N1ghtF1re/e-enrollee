package men.brakh.enrollment.domain.universityApplication.repository;

import java.util.List;
import men.brakh.enrollment.domain.universityApplication.EducationType;
import men.brakh.enrollment.domain.universityApplication.UniversityApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityApplicationRepository extends JpaRepository<UniversityApplication, Integer> {
    List<UniversityApplication> findByEnrolleeId(Integer enrolleeId);
    List<UniversityApplication> findByEnrolleeIdAndType(Integer enrolleeId, EducationType type);
}
