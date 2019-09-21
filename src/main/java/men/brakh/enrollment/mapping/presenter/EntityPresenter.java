package men.brakh.enrollment.mapping.presenter;

import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.Dto;

import java.util.List;

public interface EntityPresenter<T extends BaseEntity, D extends Dto> {
    D mapToDto(T entity, Class<? extends D> dtoClass);
    List<D> mapListToDto(List<T> entities, Class<? extends D> dtoClass);
}
