package men.brakh.abiturient.template;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.mapping.mapper.UpdateDtoMapper;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.Dto;
import men.brakh.abiturient.model.UpdateDto;
import men.brakh.abiturient.repository.CRUDRepository;
import men.brakh.abiturient.utils.ValidationUtils;

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

    private final CRUDRepository<T, I> repository;
    private final UpdateDtoMapper<R, T> dtoMapper;
    private final EntityPresenter<T, D> entityPresenter;
    private Validator validator;

    public UpdateTemplate(final CRUDRepository<T, I> repository,
                          final UpdateDtoMapper<R, T> dtoMapper,
                          final EntityPresenter<T, D> entityPresenter,
                          final Validator validator) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
        this.entityPresenter = entityPresenter;
        this.validator = validator;
    }

    public UpdateTemplate(final CRUDRepository<T, I> repository,
                          final UpdateDtoMapper<R, T> dtoMapper,
                          final EntityPresenter<T, D> entityPresenter) {
        this(repository, dtoMapper, entityPresenter, null);
    }


    protected void beforeSaving(T entity, R request) throws BadRequestException {
        ValidationUtils.validateAndThowIfInvalid(validator, entity);
    }

    protected void afterSaving(T entity) {

    }

    public D update(I id, R request, Class<? extends D> dtoClass) throws BadRequestException {
        T entity = repository.findById(id).orElseThrow(() -> new BadRequestException("Entity isn't found"));
        entity = dtoMapper.mapToEntity(entity, request);

        entity.setId(id);

        beforeSaving(entity, request);
        try {
            entity = repository.update(entity);
        } catch (Exception e) {
            throw new BadRequestException(e);
        }

        afterSaving(entity);
        return entityPresenter.mapToDto(entity, dtoClass);
    }
}
