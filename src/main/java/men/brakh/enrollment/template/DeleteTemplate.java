package men.brakh.enrollment.template;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.repository.CRUDRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deleting teamplte
 * @param <T> entity type
 * @param <I> identifier's type
 */
public class DeleteTemplate<
        T extends BaseEntity,
        I> {

    private static final Logger logger = LoggerFactory.getLogger(DeleteTemplate.class);


    private final CRUDRepository<T, I> repository;


    public DeleteTemplate(CRUDRepository<T, I> repository) {
        this.repository = repository;
    }

    /**
     * Before deleting hook
     * @param entity entity to delete
     */
    protected void beforeDeleting(T entity) {

    }


    /**
     * After deleting hook
     * @param entity removed enityt.
     */
    protected void afterDeleting(T entity) {

    }

    /**
     * Delete entity from db
     * @param id id
     * @throws BadRequestException if something went wrong.
     */
    public void delete(I id) throws BadRequestException {
        T entity = repository.findById(id).orElseThrow(() -> new BadRequestException("Entity isn't found"));

        beforeDeleting(entity);

        try {
            repository.delete(id);
        } catch (Exception e) {
            logger.error(entity.getClass().getSimpleName() + " with id " + id + " deleting error", e);
            throw new BadRequestException(e);
        }

        afterDeleting(entity);
    }
}
