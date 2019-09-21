package men.brakh.enrollment.model.enrollee.repository;

import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.repository.impl.JsonCRUDRepository;

public class EnrolleeJsonRepository extends JsonCRUDRepository<Enrollee, Integer> implements EnrolleeRepository {

    public EnrolleeJsonRepository() {
        super(Enrollee.class, "enrollee", true);
    }

}
