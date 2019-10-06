package men.brakh.enrollment.controller.impl;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.controller.ConsoleCRUDController;
import men.brakh.enrollment.controller.SearchController;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.enrollment.model.educationDocument.service.EducationDocumentService;

import java.util.*;
import java.util.function.BiConsumer;

public class EducationDocumentConsoleCRUDController implements ConsoleCRUDController {
    private Scanner scanner = new Scanner(System.in);
    private EducationDocumentService educationDocumentService = Config.educationDocumentService;

    private SearchController<EducationDocumentDto> searchController = new SearchController<>(
            educationDocumentService, EducationDocumentDto.class
    );

    private Map<String, BiConsumer<EducationDocumentCreateRequest.EducationDocumentCreateRequestBuilder, String>> createFieldsMap
            = new LinkedHashMap<String, BiConsumer<EducationDocumentCreateRequest.EducationDocumentCreateRequestBuilder, String>>() {{
        put("Enrollee Id", (builder, value) -> builder.enrolleeId(Integer.parseInt(value)));
        put("Average grade", (builder, value) -> builder.averageGrade(Double.parseDouble(value)));

        put("Document type", EducationDocumentCreateRequest.EducationDocumentCreateRequestBuilder::documentType);
        put("Document unique number", EducationDocumentCreateRequest.EducationDocumentCreateRequestBuilder::documentUniqueNumber);
        put("Education Institution", EducationDocumentCreateRequest.EducationDocumentCreateRequestBuilder::educationalInstitution);
    }};

    private Map<String, BiConsumer<EducationDocumentUpdateRequest.EducationDocumentUpdateRequestBuilder, String>> updateFieldMap
            = new LinkedHashMap<String, BiConsumer<EducationDocumentUpdateRequest.EducationDocumentUpdateRequestBuilder, String>>() {{
        put("Average grade", (builder, value) -> builder.averageGrade(Double.parseDouble(value)));

        put("Document type", EducationDocumentUpdateRequest.EducationDocumentUpdateRequestBuilder::documentType);
        put("Document unique number", EducationDocumentUpdateRequest.EducationDocumentUpdateRequestBuilder::documentUniqueNumber);
        put("Education Institution", EducationDocumentUpdateRequest.EducationDocumentUpdateRequestBuilder::educationalInstitution);
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
        final EducationDocumentCreateRequest.EducationDocumentCreateRequestBuilder builder
                = EducationDocumentCreateRequest.builder();


        createFieldsMap.forEach((fieldName, func) -> {
            System.out.println("Enter " + fieldName);
            String value = scanner.nextLine();
            func.accept(builder, value);
        });

        educationDocumentService.create(builder.build());
    }

    @Override
    public void showList() {
        System.out.println("\n");
        List<EducationDocumentDto> list = educationDocumentService.getAll();

        if (list.isEmpty()) {
            System.out.println("There is not data :C");
            return;

        }

        list.forEach(dto -> System.out.println(dto.toString()));
        System.out.println("\n");
    }

    @Override
    public void update() throws BadRequestException {
        final EducationDocumentUpdateRequest.EducationDocumentUpdateRequestBuilder builder = EducationDocumentUpdateRequest.builder();

        System.out.println("\nEnter id\n");

        int id = getId();

        System.out.println("You'r changing following education document: \n" + educationDocumentService.getById(id).toString());

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

        educationDocumentService.update(id, builder.build());
    }

    @Override
    public void delete() throws BadRequestException {
        System.out.println("Please, enter id");
        int id = getId();

        educationDocumentService.delete(id);
    }

    @Override
    public void search() throws BadRequestException {
        searchController.search();
    }
}
