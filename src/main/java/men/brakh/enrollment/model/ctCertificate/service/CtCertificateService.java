package men.brakh.enrollment.model.ctCertificate.service;

import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateUpdateRequest;
import men.brakh.enrollment.service.CRUDEntityService;

import java.util.List;

public interface CtCertificateService extends CRUDEntityService<
        CtCertificateDto,
        CtCertificateCreateRequest,
        CtCertificateUpdateRequest,
        Integer> {
    List<CtCertificateDto> getByEnrolleeId(final Integer enrolleeId);
}
