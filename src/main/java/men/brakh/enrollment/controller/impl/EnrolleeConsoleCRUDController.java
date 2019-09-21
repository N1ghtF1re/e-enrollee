package men.brakh.enrollment.controller.impl;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.controller.ConsoleCRUDController;
import men.brakh.enrollment.controller.SearchController;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeCreateRequest;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeUpdateRequest;
import men.brakh.enrollment.model.enrollee.service.EnrolleeService;

import java.util.*;
import java.util.function.BiConsumer;

public class EnrolleeConsoleCRUDController implements ConsoleCRUDController {
    private Scanner scanner = new Scanner(System.in);
    private EnrolleeService enrolleeService = Config.enrolleeService;
    private SearchController<EnrolleeDto> searchController = new SearchController<>(enrolleeService, EnrolleeDto.class);

    private Map<String, BiConsumer<EnrolleeCreateRequest.EnrolleeCreateRequestBuilder, String>> createFieldsMap
            = new LinkedHashMap<String, BiConsumer<EnrolleeCreateRequest.EnrolleeCreateRequestBuilder, String>>() {{
        put("First Name", EnrolleeCreateRequest.EnrolleeCreateRequestBuilder::firstName);
        put("Last Name", EnrolleeCreateRequest.EnrolleeCreateRequestBuilder::lastName);
        put("Middle Name", EnrolleeCreateRequest.EnrolleeCreateRequestBuilder::middleName);
        put("Birth Date", EnrolleeCreateRequest.EnrolleeCreateRequestBuilder::birthDate);
    }};

    private Map<String, BiConsumer<EnrolleeUpdateRequest.EnrolleeUpdateRequestBuilder, String>> updateFieldMap
            = new LinkedHashMap<String, BiConsumer<EnrolleeUpdateRequest.EnrolleeUpdateRequestBuilder, String>>() {{
        put("First Name", EnrolleeUpdateRequest.EnrolleeUpdateRequestBuilder::firstName);
        put("Last Name", EnrolleeUpdateRequest.EnrolleeUpdateRequestBuilder::lastName);
        put("Middle Name", EnrolleeUpdateRequest.EnrolleeUpdateRequestBuilder::middleName);
        put("Birth Date", EnrolleeUpdateRequest.EnrolleeUpdateRequestBuilder::birthDate);
    }};

    private int getId() throws BadRequestException {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new BadRequestException("Id must be int");
        } finally {
            scanner.nextLine();
        }
    }

    @Override
    public void create() throws BadRequestException {
        final EnrolleeCreateRequest.EnrolleeCreateRequestBuilder builder = EnrolleeCreateRequest.builder();


        createFieldsMap.forEach((fieldName, func) -> {
            System.out.println("Enter " + fieldName);
            String value = scanner.nextLine();
            func.accept(builder, value);
        });

        enrolleeService.create(builder.build());
    }

    @Override
    public void showList() {
        System.out.println("\n");
        List<EnrolleeDto> list = enrolleeService.getAll();

        if (list.isEmpty()) {
            System.out.println("There is not data :C");
        }

        list.stream()
                .sorted()
                .forEach(enrolleeDto -> System.out.println(enrolleeDto.toString()));
        System.out.println("\n");
    }

    @Override
    public void update() throws BadRequestException {
        final EnrolleeUpdateRequest.EnrolleeUpdateRequestBuilder builder = EnrolleeUpdateRequest.builder();

        System.out.println("\nEnter id\n");

        int id = getId();

        System.out.println("You'r changing following enrollee: \n" + enrolleeService.getById(id).toString());

        System.out.println("\nPlease, enter data if you want to update it. " +
                "\nIf you don't want update the field, just press 'enter'");

        updateFieldMap.forEach((fieldName, func) -> {
            System.out.println(fieldName);
            String value = scanner.nextLine();
            if (!value.replaceAll(" ", "").isEmpty()) {
                System.out.println("New " + fieldName + ": " + value);
                func.accept(builder, value);
            }
        });

        enrolleeService.update(id, builder.build());
    }

    @Override
    public void delete() throws BadRequestException {
        System.out.println("Please, enter id");
        int id = getId();

        enrolleeService.delete(id);
    }

    @Override
    public void search() throws BadRequestException {
        searchController.search();
    }
}
