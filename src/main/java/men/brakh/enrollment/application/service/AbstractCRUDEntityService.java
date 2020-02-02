package men.brakh.enrollment.application.service;

import javax.validation.Validator;
import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.CreateDto;
import men.brakh.enrollment.domain.Dto;
import men.brakh.enrollment.domain.UpdateDto;
import men.brakh.enrollment.application.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.application.mapping.mapper.DtoMapper;
import men.brakh.enrollment.application.mapping.mapper.UpdateDtoMapper;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.application.template.CreateTemplate;
import men.brakh.enrollment.application.template.DeleteTemplate;
import men.brakh.enrollment.application.template.GetTemplate;
import men.brakh.enrollment.application.template.UpdateTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Abstract CRUD Service
 * @param <T> Entity Class
 * @param <D> Presented DTO Class
 * @param <C> Creation Request DTO Class
 * @param <U> Updating Request DTO Class
 * @param <I> Identifier's type
 */
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

    public AbstractCRUDEntityService(final JpaRepository<T, I> crudRepository,
                                    final DtoMapper<D, T> dtoMapper,
                                    final EntityPresenter<T, D> entityPresenter,
                                    final Validator validator) {

        this.createTemplate = new CreateTemplate<>(crudRepository,
                (CreateDtoMapper<C, T>) dtoMapper,
                entityPresenter,
                validator);

        this.updateTemplate = new UpdateTemplate<>(crudRepository,
                (UpdateDtoMapper<U, T>) dtoMapper,
                entityPresenter,
                validator);

        this.deleteTemplate = new DeleteTemplate<>(crudRepository);

        this.getTemplate = new GetTemplate<>(entityPresenter, crudRepository);
    }
}
