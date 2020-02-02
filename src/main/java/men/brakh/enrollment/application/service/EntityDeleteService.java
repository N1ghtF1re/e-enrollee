package men.brakh.enrollment.application.service;

import men.brakh.enrollment.exception.BadRequestException;

public interface EntityDeleteService<I> {
    void delete(I id) throws BadRequestException;
}
