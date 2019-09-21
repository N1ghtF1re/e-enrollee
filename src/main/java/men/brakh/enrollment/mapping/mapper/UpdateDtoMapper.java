package men.brakh.enrollment.mapping.mapper;

import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.UpdateDto;

public interface UpdateDtoMapper<D extends UpdateDto, T extends BaseEntity> {
    T mapToEntity(T entity, D updateRequest);
}