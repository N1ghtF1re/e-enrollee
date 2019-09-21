package men.brakh.enrollment.repository;

import men.brakh.enrollment.exception.ResourceNotFoundException;
import men.brakh.enrollment.model.BaseEntity;

public interface UpdateRepository<T extends BaseEntity> {
    T update (T entity) throws ResourceNotFoundException;
}
