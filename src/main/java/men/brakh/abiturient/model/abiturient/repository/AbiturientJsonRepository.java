package men.brakh.abiturient.model.abiturient.repository;

import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.repository.impl.JsonCRUDRepository;

public class AbiturientJsonRepository extends JsonCRUDRepository<Abiturient, Integer> implements AbiturientRepository {

    public AbiturientJsonRepository() {
        super(Abiturient.class, "abiturient", true);
    }
}
