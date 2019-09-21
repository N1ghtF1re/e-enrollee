package men.brakh.enrollment.controller;

import men.brakh.enrollment.exception.BadRequestException;

public interface ConsoleCRUDController {
    void create() throws BadRequestException;
    void showList();
    void update() throws BadRequestException;
    void delete() throws BadRequestException;
    void search() throws BadRequestException;
}
