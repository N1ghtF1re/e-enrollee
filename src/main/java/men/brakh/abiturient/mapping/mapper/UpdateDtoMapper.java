package men.brakh.abiturient.mapping.mapper;

import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.UpdateDto;

public interface UpdateDtoMapper<D extends UpdateDto, T extends BaseEntity> {
    T mapToEntity(T entity, D updateRequest);
}