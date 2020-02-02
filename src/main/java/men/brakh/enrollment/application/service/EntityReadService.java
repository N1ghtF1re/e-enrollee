package men.brakh.enrollment.application.service;

import java.util.List;
import men.brakh.enrollment.domain.Dto;
import men.brakh.enrollment.exception.BadRequestException;

public interface EntityReadService<D extends Dto, I> {
    D getById(I id) throws BadRequestException;
    List<D> getAll();
}
