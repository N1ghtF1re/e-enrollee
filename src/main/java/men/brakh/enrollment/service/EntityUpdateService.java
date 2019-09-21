package men.brakh.enrollment.service;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.Dto;
import men.brakh.enrollment.model.UpdateDto;

public interface EntityUpdateService<
        D extends Dto,
        U extends UpdateDto,
        I> {
    D update(I id, U updateRequest) throws BadRequestException;
}
