package men.brakh.abiturient.mapping.mapper;

import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.CreateDto;

public interface CreateDtoMapper<D extends CreateDto, T extends BaseEntity> {
    T mapToEntity(D createRequest);
}
