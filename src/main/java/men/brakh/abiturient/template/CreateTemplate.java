package men.brakh.abiturient.template;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.mapping.mapper.CreateDtoMapper;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.CreateDto;
import men.brakh.abiturient.model.Dto;
import men.brakh.abiturient.repository.CRUDRepository;
import men.brakh.abiturient.repository.CreateRepository;
import men.brakh.abiturient.utils.ValidationUtils;

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

    private final CreateRepository<T> repository;
    private final CreateDtoMapper<R, T> dtoMapper;
    private final EntityPresenter<T, D> entityPresenter;
    private Validator validator;

    public CreateTemplate(final CreateRepository<T> repository,
                          final CreateDtoMapper<R, T> dtoMapper,
                          final EntityPresenter<T, D> entityPresenter,
                          final Validator validator) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
        this.entityPresenter = entityPresenter;
        this.validator = validator;
    }

    public CreateTemplate(final CRUDRepository<T, ?> repository,
                          final CreateDtoMapper<R, T> dtoMapper,
                          final EntityPresenter<T, D> entityPresenter) {
        this(repository, dtoMapper, entityPresenter, null);
    }


    protected void beforeSaving(T entity, R request) throws BadRequestException {
        ValidationUtils.validateAndThowIfInvalid(validator, entity);
    }

    protected void afterSaving(T entity) {

    }

    public D save(R request, Class<? extends D> dtoClass) throws BadRequestException {
        T entity = dtoMapper.mapToEntity(request);
        beforeSaving(entity, request);
        try {
            entity = repository.create(entity);
        } catch (Exception e) {
            throw new BadRequestException(e);
        }

        afterSaving(entity);
        return entityPresenter.mapToDto(entity, dtoClass);
    }
}
