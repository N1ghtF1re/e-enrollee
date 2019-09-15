package men.brakh.abiturient.repository;

import men.brakh.abiturient.model.BaseEntity;

public interface CreateRepository<T extends BaseEntity> {
    T create(T entity);
}
