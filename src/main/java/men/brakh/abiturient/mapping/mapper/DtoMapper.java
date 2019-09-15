package men.brakh.abiturient.mapping.mapper;

import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.Dto;

public interface DtoMapper <D extends Dto, T extends BaseEntity> {
    T mapToEntity(D dto);
}
