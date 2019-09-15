package men.brakh.abiturient.service;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.model.Dto;

import java.util.List;

public interface EntityReadService<D extends Dto, I> {
    D getById(I id) throws BadRequestException;
    List<D> getAll();
}
