package men.brakh.enrollment.application.service;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.domain.Dto;
import men.brakh.enrollment.domain.UpdateDto;

public interface EntityUpdateService<
        D extends Dto,
        U extends UpdateDto,
        I> {
    D update(I id, U updateRequest) throws BadRequestException;
}
