package men.brakh.abiturient.model.ctCertificate.repository;

import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.repository.CRUDRepository;

import java.util.List;

public interface CtCertificateRepository extends CRUDRepository<CtCertificate, Integer> {
    List<CtCertificate> findByAbiturientId(Integer abiturientId);
    List<CtCertificate> findByYearAndSubject(Integer year, Subject subject);
    List<CtCertificate> findByCertificateIdentifiers(String certificateIdentifier, String certificateNumber);
}
