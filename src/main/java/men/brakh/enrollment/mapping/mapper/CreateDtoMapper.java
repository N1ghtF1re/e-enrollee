package men.brakh.enrollment.mapping.mapper;

import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.CreateDto;

public interface CreateDtoMapper<D extends CreateDto, T extends BaseEntity> {
    T mapToEntity(D createRequest);
}
