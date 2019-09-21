package men.brakh.enrollment.model.universityApplication.repository;

import men.brakh.enrollment.model.universityApplication.UniversityApplication;
import men.brakh.enrollment.model.universityApplication.UniversityApplicationType;
import men.brakh.enrollment.repository.CRUDRepository;

import java.util.List;

public interface UniversityApplicationRepository extends CRUDRepository<UniversityApplication, Integer> {
    List<UniversityApplication> findByEnrolleeId(Integer enrolleeId);
    List<UniversityApplication> findByEnrolleeIdAndType(Integer enrolleeId, UniversityApplicationType type);
}
