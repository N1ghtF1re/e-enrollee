package men.brakh.enrollment.application.mapping.mapper;

import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.UpdateDto;

public interface UpdateDtoMapper<D extends UpdateDto, T extends BaseEntity> {
    T mapToEntity(T entity, D updateRequest);
}