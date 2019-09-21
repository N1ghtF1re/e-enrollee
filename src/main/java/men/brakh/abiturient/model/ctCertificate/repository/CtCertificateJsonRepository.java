package men.brakh.abiturient.model.ctCertificate.repository;

import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.repository.impl.JsonCRUDRepository;

import java.util.List;

public class CtCertificateJsonRepository
        extends JsonCRUDRepository<CtCertificate, Integer>
        implements CtCertificateRepository {

    private final AbiturientRepository abiturientRepository;

    public CtCertificateJsonRepository(final AbiturientRepository abiturientRepository) {
        super(CtCertificate.class, "ct-certificate", true);
        this.abiturientRepository = abiturientRepository;
    }


    @Override
    protected CtCertificate postProcessEntity(final CtCertificate entity) {
        entity.setAbiturient(abiturientRepository.findById(
                entity.getAbiturient().getId()
        ).orElse(null));
        return super.postProcessEntity(entity);
    }


    @Override
    public List<CtCertificate> findByAbiturientId(final Integer abiturientId) {
        return find(ctCertificate -> ctCertificate
                .getAbiturient().getId().equals(abiturientId));
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
