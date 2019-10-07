package men.brakh.enrollment.template;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.CreateDto;
import men.brakh.enrollment.model.Dto;
import men.brakh.enrollment.repository.CRUDRepository;
import men.brakh.enrollment.repository.CreateRepository;
import men.brakh.enrollment.validation.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;

/**
 * Template for data creation.
 * @param <T> Entity type
 * @param <R> Create Request DTO
 * @param <D> DTO which will be returned after creation
 */
public class CreateTemplate<
        T extends BaseEntity,
        R extends CreateDto,
        D extends Dto>  {

    private static final Logger logger = LoggerFactory.getLogger(CreateTemplate.class);

    private final CreateRepository<T> repository;
    private final CreateDtoMapper<R, T> dtoMapper;
    private final EntityPresenter<T, D> entityPresenter;
    private Validator validator;

    /**
     * Constructor.
     * @param repository repository for entity
     * @param dtoMapper DTO Mapper for entity
     * @param entityPresenter Entity presenter for entity
     * @param validator validator
     */
    public CreateTemplate(final CreateRepository<T> repository,
                          final CreateDtoMapper<R, T> dtoMapper,
                          final EntityPresenter<T, D> entityPresenter,
                          final Validator validator) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
        this.entityPresenter = entityPresenter;
        this.validator = validator;
    }


    /**
     * Constructor without validator. Entities won't be validated before saving.
     * @param repository repository for entity
     * @param dtoMapper DTO Mapper for entity
     * @param entityPresenter Entity presenter for entity
     */
    public CreateTemplate(final CRUDRepository<T, ?> repository,
                          final CreateDtoMapper<R, T> dtoMapper,
                          final EntityPresenter<T, D> entityPresenter) {
        this(repository, dtoMapper, entityPresenter, null);
    }


    /**
     * Before saving hook.
     * @param entity entity
     * @param request request
     * @throws BadRequestException if something went wrong
     */
    protected void beforeSaving(T entity, R request) throws BadRequestException {
        ValidationUtils.validateAndThowIfInvalid(validator, entity);
    }

    /**
     * After saving hook.
     * @param entity saved entity.
     */
    protected void afterSaving(T entity) {

    }

    /**
     * Save entity in db and return mepped to dto
     * @param request creation request
     * @param dtoClass class of dto which need to return
     * @return dto
     * @throws BadRequestException if something went wrong.
     */
    public D save(R request, Class<? extends D> dtoClass) throws BadRequestException {
        T entity = dtoMapper.mapToEntity(request);
        beforeSaving(entity, request);
        try {
            entity = repository.create(entity);
        } catch (Exception e) {
            logger.error(entity.getClass().getSimpleName() + " " + entity.toString() + "creation error", e);
            throw new BadRequestException(e);
        }

        afterSaving(entity);
        return entityPresenter.mapToDto(entity, dtoClass);
    }
}
