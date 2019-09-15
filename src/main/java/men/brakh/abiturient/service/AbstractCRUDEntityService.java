package men.brakh.abiturient.service;

import men.brakh.abiturient.mapping.mapper.CreateDtoMapper;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.mapper.UpdateDtoMapper;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.CreateDto;
import men.brakh.abiturient.model.Dto;
import men.brakh.abiturient.model.UpdateDto;
import men.brakh.abiturient.repository.CRUDRepository;
import men.brakh.abiturient.template.CreateTemplate;
import men.brakh.abiturient.template.DeleteTemplate;
import men.brakh.abiturient.template.GetTemplate;
import men.brakh.abiturient.template.UpdateTemplate;

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
