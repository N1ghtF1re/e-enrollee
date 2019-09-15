package men.brakh.abiturient.template;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.Dto;
import men.brakh.abiturient.repository.ReadRepository;

import java.util.List;

public class GetTemplate<T extends BaseEntity,
        D extends Dto,
        I> {

    private final EntityPresenter presenter;
    private final ReadRepository<T, I> repository;

    public GetTemplate(EntityPresenter presenter, ReadRepository<T, I> repository) {
        this.presenter = presenter;
        this.repository = repository;
    }

    public D getById(I id, Class<D> dtoClass) throws BadRequestException {
        T entity = repository.findById(id).orElseThrow(() -> new BadRequestException("Entity isn't found"));
        return (D) presenter.mapToDto(entity, dtoClass);
    }

    public List<D> getAll(Class<D> dtoClass) {
        List<T> entities = repository.findAll();
        return presenter.mapListToDto(entities, dtoClass);
    }


}
