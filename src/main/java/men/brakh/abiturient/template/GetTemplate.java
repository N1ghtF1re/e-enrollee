package men.brakh.abiturient.template;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.Dto;
import men.brakh.abiturient.repository.ReadRepository;

import java.util.List;
import java.util.function.Supplier;

public class GetTemplate<T extends BaseEntity,
        D extends Dto,
        I> {

    private final EntityPresenter presenter;
    private final ReadRepository<T, I> repository;

    public GetTemplate(final EntityPresenter presenter, final ReadRepository<T, I> repository) {
        this.presenter = presenter;
        this.repository = repository;
    }

    public D getById(final I id, final Class<D> dtoClass) throws BadRequestException {
        T entity = repository.findById(id).orElseThrow(() -> new BadRequestException(
                "Entity " + dtoClass.getSimpleName().replaceAll("Dto", "") + " with id " + id + "isn't found")
        );
        return (D) presenter.mapToDto(entity, dtoClass);
    }

    public List<D> getAll(final Class<D> dtoClass) {
        List<T> entities = repository.findAll();
        return presenter.mapListToDto(entities, dtoClass);
    }

    public List<D> findBy(final Supplier<List<T>> repositoryFunctionLambda,
                          final Class<D> dtoClass) {
        List<T> entities = repositoryFunctionLambda.get();
        return presenter.mapListToDto(entities, dtoClass);
    }
}
