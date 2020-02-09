package men.brakh.enrollment.domain.enrollee.repository;

import men.brakh.enrollment.domain.enrollee.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnrolleeRepository extends JpaRepository<Enrollee, Integer>,
    JpaSpecificationExecutor<Enrollee> {

}
