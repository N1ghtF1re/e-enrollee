package men.brakh.enrollment.service;

import men.brakh.enrollment.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.mapping.mapper.DtoMapper;
import men.brakh.enrollment.mapping.mapper.UpdateDtoMapper;
import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.CreateDto;
import men.brakh.enrollment.model.Dto;
import men.brakh.enrollment.model.UpdateDto;
import men.brakh.enrollment.repository.CRUDRepository;
import men.brakh.enrollment.template.CreateTemplate;
import men.brakh.enrollment.template.DeleteTemplate;
import men.brakh.enrollment.template.GetTemplate;
import men.brakh.enrollment.template.UpdateTemplate;

import javax.validation.Validator;

public abstract class AbstractCRUDEntityService<
        T extends BaseEntity,
        D extends Dto,
        C extends CreateDto,
        U extends UpdateDto,
        I> implements CRUDEntityService<D, C, U, I> {

    protected final CreateTemplate<T, C, D> createTemplate;

    protected final UpdateTemplate<T, U, D, I> updateTemplate;

    protected final GetTemplate<T, D, I> getTemplate;

    protected final DeleteTemplate<T, I> deleteTemplate;

    public AbstractCRUDEntityService(final CRUDRepository<T, I> crudRepository,
                                    final DtoMapper<D, T> dtoMapper,
                                    final EntityPresenter<T, D> entityPresenter,
                                    final Validator validator) {

        this.createTemplate = new CreateTemplate<>(crudRepository,
                (CreateDtoMapper<C, T>) dtoMapper,
                entityPresenter,
                validator);

        this.updateTemplate = new UpdateTemplate<>(crudRepository,
                (UpdateDtoMapper<U, T>) dtoMapper,
                entityPresenter);

        this.deleteTemplate = new DeleteTemplate<>(crudRepository);

        this.getTemplate = new GetTemplate<>(entityPresenter, crudRepository);
    }
}
