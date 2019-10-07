package men.brakh.enrollment.template;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.mapping.mapper.UpdateDtoMapper;
import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.Dto;
import men.brakh.enrollment.model.UpdateDto;
import men.brakh.enrollment.repository.CRUDRepository;
import men.brakh.enrollment.validation.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;

/**
 * Template for data updating.
 * @param <T> Entity type
 * @param <R> Create Request DTO
 * @param <D> DTO which will be returned after creation
 * @param <I> Entity's identifier.
 */
public class UpdateTemplate<
        T extends BaseEntity,
        R extends UpdateDto,
        D extends Dto,
        I>  {

    private static final Logger logger = LoggerFactory.getLogger(UpdateTemplate.class);


    private final CRUDRepository<T, I> repository;
    private final UpdateDtoMapper<R, T> dtoMapper;
    private final EntityPresenter<T, D> entityPresenter;
    private Validator validator;

    /**
     * Constructor.
     * @param repository repository for entity
     * @param dtoMapper DTO Mapper for entity
     * @param entityPresenter Entity presenter for entity
     * @param validator validator
     */
    public UpdateTemplate(final CRUDRepository<T, I> repository,
                          final UpdateDtoMapper<R, T> dtoMapper,
                          final EntityPresenter<T, D> entityPresenter,
                          final Validator validator) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
        this.entityPresenter = entityPresenter;
        this.validator = validator;
    }

    /**
     * Constructor without validator. Entities won't be validated before updating.
     * @param repository repository for entity
     * @param dtoMapper DTO Mapper for entity
     * @param entityPresenter Entity presenter for entity
     */
    public UpdateTemplate(final CRUDRepository<T, I> repository,
                          final UpdateDtoMapper<R, T> dtoMapper,
                          final EntityPresenter<T, D> entityPresenter) {
        this(repository, dtoMapper, entityPresenter, null);
    }


    /**
     * Before saving hook
     * @param entity entity
     * @param request request
     * @throws BadRequestException if something went wrong
     */
    protected void beforeSaving(T entity, R request) throws BadRequestException {
        ValidationUtils.validateAndThowIfInvalid(validator, entity);
    }

    /**
     * After saving hook
     * @param entity entity
     */
    protected void afterSaving(T entity) {

    }


    /**
     * Update entity by id and return updated entity mapped to dto
     * @param id id
     * @param request updating request
     * @param dtoClass dto class
     * @return updated entity mapped to dto
     * @throws BadRequestException if something went wrong
     */
    public D update(I id, R request, Class<? extends D> dtoClass) throws BadRequestException {
        T entity = repository.findById(id).orElseThrow(() -> new BadRequestException("Entity isn't found"));
        entity = dtoMapper.mapToEntity(entity, request);

        entity.setId(id);

        beforeSaving(entity, request);
        try {
            entity = repository.update(entity);
        } catch (Exception e) {
            logger.error(entity.getClass().getSimpleName() + " " + entity.toString() + " updating error", e);
            throw new BadRequestException(e);
        }

        afterSaving(entity);
        return entityPresenter.mapToDto(entity, dtoClass);
    }
}
