package men.brakh.abiturient.repository;

import men.brakh.abiturient.exception.RecourseNotFoundException;
import men.brakh.abiturient.model.BaseEntity;

public interface UpdateRepository<T extends BaseEntity> {
    T update (T entity) throws RecourseNotFoundException;
}
