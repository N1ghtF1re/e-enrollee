package men.brakh.enrollment.mapping.mapper;

import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.Dto;

public interface DtoMapper <D extends Dto, T extends BaseEntity> {
    T mapToEntity(D dto);
}
