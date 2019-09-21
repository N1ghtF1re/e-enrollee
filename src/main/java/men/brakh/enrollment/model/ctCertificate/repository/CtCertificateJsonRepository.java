package men.brakh.enrollment.model.ctCertificate.repository;

import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.ctCertificate.Subject;
import men.brakh.enrollment.repository.impl.JsonCRUDRepository;

import java.util.List;

public class CtCertificateJsonRepository
        extends JsonCRUDRepository<CtCertificate, Integer>
        implements CtCertificateRepository {

    private final EnrolleeRepository enrolleeRepository;

    public CtCertificateJsonRepository(final EnrolleeRepository enrolleeRepository) {
        super(CtCertificate.class, "ct-certificate", true);
        this.enrolleeRepository = enrolleeRepository;
    }


    @Override
    protected CtCertificate postProcessEntity(final CtCertificate entity) {
        entity.setEnrollee(enrolleeRepository.findById(
                entity.getEnrollee().getId()
        ).orElse(null));
        return super.postProcessEntity(entity);
    }


    @Override
    public List<CtCertificate> findByEnrolleeId(final Integer enrolleeId) {
        return find(ctCertificate -> ctCertificate
                .getEnrollee().getId().equals(enrolleeId));
    }

    @Override
    public List<CtCertificate> findByYearAndSubject(final Integer year, final Subject subject) {
        return find(ctCertificate ->
                ctCertificate.getSubject().equals(subject) && ctCertificate.getYear().equals(year));
    }

    @Override
    public List<CtCertificate> findByCertificateIdentifiers(final String certificateIdentifier, final String certificateNumber) {
        return find(ctCertificate ->
                ctCertificate.getCertificateIdentifier().equals(certificateIdentifier) &&
                ctCertificate.getCertificateNumber().equals(certificateNumber));
    }
}
