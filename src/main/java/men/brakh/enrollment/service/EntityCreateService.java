package men.brakh.enrollment.service;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.CreateDto;
import men.brakh.enrollment.model.Dto;

public interface EntityCreateService<
        D extends Dto,
        C extends CreateDto> {
    D create(C createRequest) throws BadRequestException;
}
