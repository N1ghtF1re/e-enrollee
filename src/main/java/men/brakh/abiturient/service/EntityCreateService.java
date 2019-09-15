package men.brakh.abiturient.service;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.model.CreateDto;
import men.brakh.abiturient.model.Dto;

public interface EntityCreateService<
        D extends Dto,
        C extends CreateDto> {
    D create(C createRequest) throws BadRequestException;
}
