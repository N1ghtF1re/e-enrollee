package men.brakh.abiturient.model.ctCertificate.repository;

import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.repository.impl.JsonCRUDRepository;

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
}
