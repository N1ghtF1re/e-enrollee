package men.brakh.abiturient.service;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.model.Dto;
import men.brakh.abiturient.model.UpdateDto;

public interface EntityUpdateService<
        D extends Dto,
        U extends UpdateDto,
        I> {
    D update(I id, U updateRequest) throws BadRequestException;
}
