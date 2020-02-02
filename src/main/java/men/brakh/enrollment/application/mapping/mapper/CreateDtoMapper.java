package men.brakh.enrollment.application.mapping.mapper;

import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.CreateDto;

public interface CreateDtoMapper<D extends CreateDto, T extends BaseEntity> {
    T mapToEntity(D createRequest);
}
