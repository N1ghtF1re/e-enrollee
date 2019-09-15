package men.brakh.abiturient.repository;

import men.brakh.abiturient.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface ReadRepository<T extends BaseEntity, I> {
    Optional<T> findById(I id);
    List<T> findAll();
}
