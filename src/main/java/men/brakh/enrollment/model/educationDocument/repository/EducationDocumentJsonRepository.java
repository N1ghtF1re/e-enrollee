package men.brakh.enrollment.model.educationDocument.repository;

import com.google.gson.GsonBuilder;
import men.brakh.enrollment.infrastructure.json.jsonadapters.BaseEntityOnlyIntIdJsonAdapter;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.repository.impl.JsonCRUDRepository;

import java.util.List;

public class EducationDocumentJsonRepository extends JsonCRUDRepository<EducationDocument, Integer>
        implements EducationDocumentRepository  {

    private final EnrolleeRepository enrolleeRepository;

    public EducationDocumentJsonRepository(final EnrolleeRepository enrolleeRepository) {
        super(EducationDocument.class, "education-document", true);
        this.enrolleeRepository = enrolleeRepository;
    }

    @Override
    protected void configureGson(final GsonBuilder gson) {
        super.configureGson(gson);
        gson.registerTypeAdapter(Enrollee.class, new BaseEntityOnlyIntIdJsonAdapter<Enrollee>());
    }

    @Override
    protected EducationDocument postProcessEntity(final EducationDocument entity) {
        entity.setEnrollee(enrolleeRepository.findById(
                entity.getEnrollee().getId()
        ).orElse(null));
        return super.postProcessEntity(entity);
    }

    @Override
    public List<EducationDocument> findByEnrolleeId(final Integer enrolleeId) {
        return find(educationDocument -> educationDocument.
                        getEnrollee().getId().equals(enrolleeId));
    }
}
