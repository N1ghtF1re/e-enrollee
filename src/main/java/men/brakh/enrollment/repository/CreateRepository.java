package men.brakh.enrollment.repository;

import men.brakh.enrollment.model.BaseEntity;

public interface CreateRepository<T extends BaseEntity> {
    T create(T entity);
}
