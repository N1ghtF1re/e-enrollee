package men.brakh.enrollment.application.mapping.presenter;

import java.util.List;
import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.Dto;

public interface EntityPresenter<T extends BaseEntity, D extends Dto> {
    D mapToDto(T entity, Class<? extends D> dtoClass);
    List<D> mapListToDto(List<T> entities, Class<? extends D> dtoClass);
}
