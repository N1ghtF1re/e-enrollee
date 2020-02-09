package men.brakh.enrollment.application.service;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.exception.ResourceNotFoundException;

public interface EntityDeleteService<I> {
    void delete(I id) throws BadRequestException, ResourceNotFoundException;
}
