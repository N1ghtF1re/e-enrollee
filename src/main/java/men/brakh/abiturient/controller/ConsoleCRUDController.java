package men.brakh.abiturient.controller;

import men.brakh.abiturient.exception.BadRequestException;

public interface ConsoleCRUDController {
    void create() throws BadRequestException;
    void showList();
    void update() throws BadRequestException;
    void delete() throws BadRequestException;
}
