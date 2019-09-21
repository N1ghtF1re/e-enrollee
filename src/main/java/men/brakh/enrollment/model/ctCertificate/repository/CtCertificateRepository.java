package men.brakh.enrollment.model.ctCertificate.repository;

import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.ctCertificate.Subject;
import men.brakh.enrollment.repository.CRUDRepository;

import java.util.List;

public interface CtCertificateRepository extends CRUDRepository<CtCertificate, Integer> {
    List<CtCertificate> findByEnrolleeId(Integer enrolleeId);
    List<CtCertificate> findByYearAndSubject(Integer year, Subject subject);
    List<CtCertificate> findByCertificateIdentifiers(String certificateIdentifier, String certificateNumber);
}
