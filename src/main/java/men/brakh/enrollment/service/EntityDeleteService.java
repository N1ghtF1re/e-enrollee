package men.brakh.enrollment.service;

import men.brakh.enrollment.exception.BadRequestException;

public interface EntityDeleteService<I> {
    void delete(I id) throws BadRequestException;
}
