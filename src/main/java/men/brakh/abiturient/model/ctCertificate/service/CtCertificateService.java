package men.brakh.abiturient.model.ctCertificate.service;

import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateUpdateRequest;
import men.brakh.abiturient.service.CRUDEntityService;

public interface CtCertificateService extends CRUDEntityService<
        CtCertificateDto,
        CtCertificateCreateRequest,
        CtCertificateUpdateRequest,
        Integer> {
}
