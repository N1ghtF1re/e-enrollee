package men.brakh.enrollment.template;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.Dto;
import men.brakh.enrollment.repository.ReadRepository;

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

    private boolean isComparable(Class<D> dtoClass) {
        return Comparable.class.isAssignableFrom(dtoClass);
    }

    public List<D> getAll(final Class<D> dtoClass) {
        List<T> entities = repository.findAll();
        List<D> dtos = presenter.mapListToDto(entities, dtoClass);
        if (isComparable(dtoClass) && dtos.size() > 1) {
            dtos.sort((o1, o2) -> ((Comparable) o1).compareTo(o2));
        }
        return dtos;
    }

    public List<D> findBy(final Supplier<List<T>> repositoryFunctionLambda,
                          final Class<D> dtoClass) {
        List<T> entities = repositoryFunctionLambda.get();
        return presenter.mapListToDto(entities, dtoClass);
    }
}
