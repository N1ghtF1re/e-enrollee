package men.brakh.abiturient.service;

import men.brakh.abiturient.exception.BadRequestException;

public interface EntityDeleteService<I> {
    void delete(I id) throws BadRequestException;
}
