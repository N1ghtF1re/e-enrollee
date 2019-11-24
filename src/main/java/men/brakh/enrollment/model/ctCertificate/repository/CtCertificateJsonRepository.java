package men.brakh.enrollment.model.ctCertificate.repository;

import com.google.gson.GsonBuilder;
import men.brakh.enrollment.infrastructure.json.jsonadapters.BaseEntityOnlyIntIdJsonAdapter;
import men.brakh.enrollment.model.enrollee.Enrollee;
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
    protected void configureGson(final GsonBuilder gson) {
        super.configureGson(gson);
        // Serialize only id
        gson.registerTypeAdapter(Enrollee.class, new BaseEntityOnlyIntIdJsonAdapter<Enrollee>());
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
    public List<CtCertificate> findByYearAndSubject(final Integer enrolleeId, final Integer year, final Subject subject) {
        return find(ctCertificate ->
                ctCertificate.getSubject().equals(subject) && ctCertificate.getYear().equals(year)
                    && ctCertificate.getEnrollee().getId().equals(enrolleeId));
    }

    @Override
    public List<CtCertificate> findByCertificateIdentifiers(final String certificateIdentifier, final String certificateNumber) {
        return find(ctCertificate ->
                ctCertificate.getCertificateIdentifier().equals(certificateIdentifier) &&
                ctCertificate.getCertificateNumber().equals(certificateNumber));
    }
}
