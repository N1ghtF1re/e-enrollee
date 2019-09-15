package men.brakh.abiturient.mapping.presenter;

import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.Dto;

import java.util.List;

public interface EntityPresenter<T extends BaseEntity, D extends Dto> {
    D mapToDto(T entity, Class<? extends D> dtoClass);
    List<D> mapListToDto(List<T> entities, Class<? extends D> dtoClass);
}
