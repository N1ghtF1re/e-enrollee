package men.brakh.enrollment.model.universityApplication.repository;

import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.ctCertificate.repository.CtCertificateRepository;
import men.brakh.enrollment.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.enrollment.model.universityApplication.UniversityApplication;
import men.brakh.enrollment.repository.impl.JsonCRUDRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UniversityApplicationJsonRepository extends JsonCRUDRepository<UniversityApplication, Integer>
        implements UniversityApplicationRepository {

    private final EnrolleeRepository enrolleeRepository;
    private final EducationDocumentRepository educationDocumentRepository;
    private final CtCertificateRepository ctCertificateRepository;

    public UniversityApplicationJsonRepository(final EnrolleeRepository enrolleeRepository,
                                   final EducationDocumentRepository educationDocumentRepository,
                                   final CtCertificateRepository ctCertificateRepository) {
        super(UniversityApplication.class, "universityApplication", true);
        this.enrolleeRepository = enrolleeRepository;
        this.educationDocumentRepository = educationDocumentRepository;
        this.ctCertificateRepository = ctCertificateRepository;
    }

    @Override
    protected UniversityApplication postProcessEntity(final UniversityApplication entity) {
        UniversityApplication universityApplication = super.postProcessEntity(entity);
        universityApplication.setEnrollee(
                enrolleeRepository.findById(
                        universityApplication.getEnrollee().getId()
                ).orElse(null)
        );
        universityApplication.setEducationDocument(
                educationDocumentRepository.findById(
                        universityApplication.getEducationDocument().getId()
                ).orElse(null)
        );
        universityApplication.setCertificates(
                universityApplication.getCertificates()
                    .stream()
                    .map(ctCertificate ->
                            ctCertificateRepository.findById(ctCertificate.getId())
                                .orElse(null))
                    .collect(Collectors.toList())
        );

        return universityApplication;
    }

    @Override
    public List<UniversityApplication> findByEnrolleeId(final Integer enrolleeId) {
        return find(universityApplication -> universityApplication.
                getEnrollee().getId().equals(enrolleeId));
    }
}
