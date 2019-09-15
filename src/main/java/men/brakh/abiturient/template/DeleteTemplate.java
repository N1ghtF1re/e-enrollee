package men.brakh.abiturient.template;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.repository.CRUDRepository;

public class DeleteTemplate<
        T extends BaseEntity,
        I> {

    private final CRUDRepository<T, I> repository;

    public DeleteTemplate(CRUDRepository<T, I> repository) {
        this.repository = repository;
    }

    protected void beforeDeleting(T entity) {

    }

    protected void afterDeleting(T entity) {

    }

    public void delete(I id) throws BadRequestException {
        T entity = repository.findById(id).orElseThrow(() -> new BadRequestException("Entity isn't found"));

        beforeDeleting(entity);

        try {
            repository.delete(id);
        } catch (Exception e) {
            throw new BadRequestException(e);
        }

        afterDeleting(entity);
    }
}
