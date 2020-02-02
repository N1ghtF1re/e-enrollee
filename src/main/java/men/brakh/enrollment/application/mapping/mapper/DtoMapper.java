package men.brakh.enrollment.application.mapping.mapper;

import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.Dto;

public interface DtoMapper <D extends Dto, T extends BaseEntity> {
    T mapToEntity(D dto);
}
