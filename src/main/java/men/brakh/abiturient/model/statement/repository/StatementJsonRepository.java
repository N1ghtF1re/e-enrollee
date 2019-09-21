package men.brakh.abiturient.model.statement.repository;

import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.ctCertificate.repository.CtCertificateRepository;
import men.brakh.abiturient.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.abiturient.model.statement.Statement;
import men.brakh.abiturient.repository.impl.JsonCRUDRepository;

import java.util.List;
import java.util.stream.Collectors;

public class StatementJsonRepository extends JsonCRUDRepository<Statement, Integer>
        implements StatementRepository {

    private final AbiturientRepository abiturientRepository;
    private final EducationDocumentRepository educationDocumentRepository;
    private final CtCertificateRepository ctCertificateRepository;

    public StatementJsonRepository(final AbiturientRepository abiturientRepository,
                                   final EducationDocumentRepository educationDocumentRepository,
                                   final CtCertificateRepository ctCertificateRepository) {
        super(Statement.class, "statement", true);
        this.abiturientRepository = abiturientRepository;
        this.educationDocumentRepository = educationDocumentRepository;
        this.ctCertificateRepository = ctCertificateRepository;
    }

    @Override
    protected Statement postProcessEntity(final Statement entity) {
        Statement statement = super.postProcessEntity(entity);
        statement.setAbiturient(
                abiturientRepository.findById(
                        statement.getAbiturient().getId()
                ).orElse(null)
        );
        statement.setEducationDocument(
                educationDocumentRepository.findById(
                        statement.getEducationDocument().getId()
                ).orElse(null)
        );
        statement.setCertificates(
                statement.getCertificates()
                    .stream()
                    .map(ctCertificate ->
                            ctCertificateRepository.findById(ctCertificate.getId())
                                .orElse(null))
                    .collect(Collectors.toList())
        );

        return statement;
    }

    @Override
    public List<Statement> findByAbiturientId(final Integer abiturientId) {
        return find(statement -> statement.
                getAbiturient().getId().equals(abiturientId));
    }
}
