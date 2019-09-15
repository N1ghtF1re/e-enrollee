package men.brakh.abiturient.model.educationDocument.repository;

import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.repository.impl.JsonCRUDRepository;

public class EducationDocumentJsonRepository extends JsonCRUDRepository<EducationDocument, Integer>
        implements EducationDocumentRepository  {

    private final AbiturientRepository abiturientRepository;

    public EducationDocumentJsonRepository(final AbiturientRepository abiturientRepository) {
        super(EducationDocument.class, "education-document", true);
        this.abiturientRepository = abiturientRepository;
    }

    @Override
    protected EducationDocument postProcessEntity(final EducationDocument entity) {
        entity.setAbiturient(abiturientRepository.findById(
                entity.getAbiturient().getId()
        ).orElse(null));
        return super.postProcessEntity(entity);
    }
}
