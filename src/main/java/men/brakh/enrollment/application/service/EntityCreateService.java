package men.brakh.enrollment.application.service;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.domain.CreateDto;
import men.brakh.enrollment.domain.Dto;

public interface EntityCreateService<
        D extends Dto,
        C extends CreateDto> {
    D create(C createRequest) throws BadRequestException;
}
