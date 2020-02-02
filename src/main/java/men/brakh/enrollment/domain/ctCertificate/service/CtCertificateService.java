package men.brakh.enrollment.domain.ctCertificate.service;

import men.brakh.enrollment.domain.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.enrollment.domain.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.domain.ctCertificate.dto.CtCertificateUpdateRequest;
import men.brakh.enrollment.application.service.CRUDEntityService;

import java.util.List;

public interface CtCertificateService extends CRUDEntityService<
        CtCertificateDto,
        CtCertificateCreateRequest,
        CtCertificateUpdateRequest,
        Integer> {
    List<CtCertificateDto> getByEnrolleeId(final Integer enrolleeId);
}
