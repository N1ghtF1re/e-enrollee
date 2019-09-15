package men.brakh.abiturient.model.ctCertificate.repository;

import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.repository.impl.JsonCRUDRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CtCertificateJsonRepository
        extends JsonCRUDRepository<CtCertificate, Integer>
        implements CtCertificateRepository {

    private AbiturientRepository abiturientRepository;

    public CtCertificateJsonRepository(AbiturientRepository abiturientRepository) {
        super(CtCertificate.class, "ct-certificate", true);
        this.abiturientRepository = abiturientRepository;
    }


    @Override
    protected List<CtCertificate> loadList() {
        return super.loadList()
                .stream()
                .peek(ctCertificate -> {
                    ctCertificate.setAbiturient(abiturientRepository.findById(
                            ctCertificate.getAbiturient().getId()
                    ).orElse(null));
                })
                .collect(Collectors.toList());
    }
}
