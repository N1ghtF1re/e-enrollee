package men.brakh.abiturient.repository;

import men.brakh.abiturient.model.BaseEntity;

/**
 * CRUD Repository interface.
 * @param <T> Entity type.
 * @param <I> Entity's identifier type.
 */
public interface CRUDRepository<T extends BaseEntity, I> extends
        CreateRepository<T>, UpdateRepository<T>, DeleteRepository<I>, ReadRepository<T, I> {

}
