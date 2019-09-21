package men.brakh.abiturient.controller.impl;

import men.brakh.abiturient.Config;
import men.brakh.abiturient.controller.ConsoleCRUDController;
import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateUpdateRequest;
import men.brakh.abiturient.model.ctCertificate.service.CtCertificateService;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class CtCertificateConsoleCRUDController implements ConsoleCRUDController {
    private Scanner scanner = new Scanner(System.in);
    private CtCertificateService ctCertificateService = Config.ctCertificateService;

    private Map<String, BiConsumer<CtCertificateCreateRequest.CtCertificateCreateRequestBuilder, String>> createFieldsMap
            = new LinkedHashMap<String, BiConsumer<CtCertificateCreateRequest.CtCertificateCreateRequestBuilder, String>>() {{
        put("Abiturient Id", (builder, value) -> builder.abiturientId(Integer.parseInt(value)));
        put("Certificate id", CtCertificateCreateRequest.CtCertificateCreateRequestBuilder::certificateId);
        put("Certificate Number", CtCertificateCreateRequest.CtCertificateCreateRequestBuilder::certificateNumber);
        put("Ct poitns", (builder, value) -> builder.ctPoints(Integer.parseInt(value)));
        put("Certificate year",  (builder, value) -> builder.year(Integer.parseInt(value)));
        put("Subject (" + Arrays.stream(Subject.values())
                        .map(Subject::getSubjectName)
                        .collect(Collectors.joining("/")) + ")",
                CtCertificateCreateRequest.CtCertificateCreateRequestBuilder::subject);
    }};

    private Map<String, BiConsumer<CtCertificateUpdateRequest.CtCertificateUpdateRequestBuilder, String>> updateFieldMap
            = new LinkedHashMap<String, BiConsumer<CtCertificateUpdateRequest.CtCertificateUpdateRequestBuilder, String>>() {{
        put("Certificate id", CtCertificateUpdateRequest.CtCertificateUpdateRequestBuilder::certificateId);
        put("Certificate Number", CtCertificateUpdateRequest.CtCertificateUpdateRequestBuilder::certificateNumber);
        put("Ct poitns", (builder, value) -> builder.ctPoints(Integer.parseInt(value)));
        put("Certificate year",  (builder, value) -> builder.year(Integer.parseInt(value)));
        put("Subject (" + Arrays.stream(Subject.values())
                        .map(Subject::getSubjectName)
                        .collect(Collectors.joining("/")) + ")",
                CtCertificateUpdateRequest.CtCertificateUpdateRequestBuilder::subject);
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
        final CtCertificateCreateRequest.CtCertificateCreateRequestBuilder builder
                = CtCertificateCreateRequest.builder();


        createFieldsMap.forEach((fieldName, func) -> {
            System.out.println("Enter " + fieldName);
            String value = scanner.nextLine();
            func.accept(builder, value);
        });

        ctCertificateService.create(builder.build());

    }

    @Override
    public void showList() {
        System.out.println("\n");
        List<CtCertificateDto> list = ctCertificateService.getAll();

        if (list.isEmpty()) {
            System.out.println("There is not data :C");
            return;

        }

        list.stream()
                .sorted()
                .forEach(dto -> System.out.println(dto.toString()));
        System.out.println("\n");
    }

    @Override
    public void update() throws BadRequestException {
        final CtCertificateUpdateRequest.CtCertificateUpdateRequestBuilder builder = CtCertificateUpdateRequest.builder();

        System.out.println("\nEnter id\n");

        int id = getId();

        System.out.println("You'r changing following certificate: \n" + ctCertificateService.getById(id).toString());

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

        ctCertificateService.update(id, builder.build());
    }

    @Override
    public void delete() throws BadRequestException {
        System.out.println("Please, enter id");
        int id = getId();

        ctCertificateService.delete(id);
    }
}
