package men.brakh.abiturient.controller.impl;

import men.brakh.abiturient.Config;
import men.brakh.abiturient.controller.ConsoleCRUDController;
import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.model.abiturient.dto.AbiturientCreateRequest;
import men.brakh.abiturient.model.abiturient.dto.AbiturientDto;
import men.brakh.abiturient.model.abiturient.dto.AbiturientUpdateRequest;
import men.brakh.abiturient.model.abiturient.service.AbiturientService;

import java.util.*;
import java.util.function.BiConsumer;

public class AbiturientConsoleCRUDController implements ConsoleCRUDController {
    private Scanner scanner = new Scanner(System.in);
    private AbiturientService abiturientService = Config.abiturientService;

    private Map<String, BiConsumer<AbiturientCreateRequest.AbiturientCreateRequestBuilder, String>> createFieldsMap
            = new LinkedHashMap<String, BiConsumer<AbiturientCreateRequest.AbiturientCreateRequestBuilder, String>>() {{
        put("First Name", AbiturientCreateRequest.AbiturientCreateRequestBuilder::firstName);
        put("Last Name", AbiturientCreateRequest.AbiturientCreateRequestBuilder::lastName);
        put("Middle Name", AbiturientCreateRequest.AbiturientCreateRequestBuilder::middleName);
        put("Birth Date", AbiturientCreateRequest.AbiturientCreateRequestBuilder::birthDate);
    }};

    private Map<String, BiConsumer<AbiturientUpdateRequest.AbiturientUpdateRequestBuilder, String>> updateFieldMap
            = new LinkedHashMap<String, BiConsumer<AbiturientUpdateRequest.AbiturientUpdateRequestBuilder, String>>() {{
        put("First Name", AbiturientUpdateRequest.AbiturientUpdateRequestBuilder::firstName);
        put("Last Name", AbiturientUpdateRequest.AbiturientUpdateRequestBuilder::lastName);
        put("Middle Name", AbiturientUpdateRequest.AbiturientUpdateRequestBuilder::middleName);
        put("Birth Date", AbiturientUpdateRequest.AbiturientUpdateRequestBuilder::birthDate);
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
        final AbiturientCreateRequest.AbiturientCreateRequestBuilder builder = AbiturientCreateRequest.builder();


        createFieldsMap.forEach((fieldName, func) -> {
            System.out.println("Enter " + fieldName);
            String value = scanner.nextLine();
            func.accept(builder, value);
        });

        abiturientService.create(builder.build());
    }

    @Override
    public void showList() {
        System.out.println("\n");
        List<AbiturientDto> list = abiturientService.getAll();

        if (list.isEmpty()) {
            System.out.println("There is not data :C");
        }

        list.stream()
                .sorted()
                .forEach(abiturientDto -> System.out.println(abiturientDto.toString()));
        System.out.println("\n");
    }

    @Override
    public void update() throws BadRequestException {
        final AbiturientUpdateRequest.AbiturientUpdateRequestBuilder builder = AbiturientUpdateRequest.builder();

        System.out.println("\nEnter id\n");

        int id = getId();

        System.out.println("You'r changing following abiturient: \n" + abiturientService.getById(id).toString());

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

        abiturientService.update(id, builder.build());
    }

    @Override
    public void delete() throws BadRequestException {
        System.out.println("Please, enter id");
        int id = getId();

        abiturientService.delete(id);
    }
}
