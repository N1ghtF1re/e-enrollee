package men.brakh.enrollment.domain.ctCertificate.repository;

import java.util.List;
import men.brakh.enrollment.domain.ctCertificate.CtCertificate;
import men.brakh.enrollment.domain.ctCertificate.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CtCertificateRepository extends JpaRepository<CtCertificate, Integer> {
    List<CtCertificate> findByEnrolleeId(Integer enrolleeId);
    List<CtCertificate> findAllByEnrolleeIdAndYearAndSubject(Integer enrolleeId, Integer year, Subject subject);
    List<CtCertificate> findAllByCertificateIdentifierAndCertificateNumber(String certificateIdentifier, String certificateNumber);
}
