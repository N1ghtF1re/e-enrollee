package men.brakh.enrollment.service;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.Dto;

import java.util.List;

public interface EntityReadService<D extends Dto, I> {
    D getById(I id) throws BadRequestException;
    List<D> getAll();
}
