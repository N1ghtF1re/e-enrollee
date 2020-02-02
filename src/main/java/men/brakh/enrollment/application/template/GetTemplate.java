package men.brakh.enrollment.application.template;

import java.util.List;
import java.util.function.Supplier;
import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.Dto;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Get template.
 * @param <T> Entity type
 * @param <D> Dto presenter type
 * @param <I> Identifier
 */
@SuppressWarnings("unchecked")
public class GetTemplate<T extends BaseEntity,
        D extends Dto,
        I> {

    private final EntityPresenter presenter;
    private final JpaRepository<T, I> repository;

    /**
     * Constructor
     * @param presenter entity presenter
     * @param repository repository
     */
    public GetTemplate(final EntityPresenter presenter, final JpaRepository<T, I> repository) {
        this.presenter = presenter;
        this.repository = repository;
    }

    /**
     * Getting entity by id and presenting as dto
     * @param id id
     * @param dtoClass dto class.
     * @return entity mapped to dto
     * @throws BadRequestException if something went wrong
     */
    public D getById(final I id, final Class<D> dtoClass) throws BadRequestException {
        T entity = repository.findById(id).orElseThrow(() -> new BadRequestException(
                "Entity " + dtoClass.getSimpleName().replaceAll("Dto", "") + " with id " + id + "isn't found")
        );
        return (D) presenter.mapToDto(entity, dtoClass);
    }

    private boolean isComparable(Class<D> dtoClass) {
        return Comparable.class.isAssignableFrom(dtoClass);
    }

    /**
     * Getting all entities and presenting it as dto.
     * @param dtoClass dto class
     * @return
     */
    public List<D> getAll(final Class<D> dtoClass) {
        List<T> entities = repository.findAll();
        List<D> dtos = presenter.mapListToDto(entities, dtoClass);
        if (isComparable(dtoClass) && dtos.size() > 1) {
            dtos.sort((o1, o2) -> ((Comparable) o1).compareTo(o2));
        }
        return dtos;
    }

    /**
     * Getting entities by repository's function call and presenting it as dto
     * @param repositoryFunctionLambda function which return list of entities (repository's function)
     * @param dtoClass dtoclass
     * @return
     */
    public List<D> findBy(final Supplier<List<T>> repositoryFunctionLambda,
                          final Class<D> dtoClass) {
        List<T> entities = repositoryFunctionLambda.get();
        return presenter.mapListToDto(entities, dtoClass);
    }
}
