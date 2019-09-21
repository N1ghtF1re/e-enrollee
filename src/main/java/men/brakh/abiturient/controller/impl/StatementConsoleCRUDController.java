package men.brakh.abiturient.controller.impl;

import men.brakh.abiturient.Config;
import men.brakh.abiturient.controller.ConsoleCRUDController;
import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.model.abiturient.service.AbiturientService;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.service.CtCertificateService;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.abiturient.model.educationDocument.service.EducationDocumentService;
import men.brakh.abiturient.model.specialty.Specialty;
import men.brakh.abiturient.model.statement.dto.StatementCreateRequest;
import men.brakh.abiturient.model.statement.dto.StatementDto;
import men.brakh.abiturient.model.statement.dto.StatementUpdateRequest;
import men.brakh.abiturient.model.statement.service.StatementService;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StatementConsoleCRUDController implements ConsoleCRUDController {
    private final Scanner scanner = new Scanner(System.in);
    private final StatementService statementService = Config.statementService;
    private final AbiturientService abiturientService = Config.abiturientService;
    private final CtCertificateService ctCertificateService = Config.ctCertificateService;
    private final EducationDocumentService educationDocumentService = Config.educationDocumentService;


    private int getIntId() throws BadRequestException {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new BadRequestException("Id must be int");
        } finally {
            scanner.nextLine();
        }
    }

    private List<Integer> getCtCertificatesList(final Integer abiturientId) {
        System.out.println("\n\nEnter ct certificates ids (coma separated). Available certificates: \n");

        final List<String> ctCertificateDtoString
                = ctCertificateService.getByAbiturientId(abiturientId)
                        .stream()
                        .map(CtCertificateDto::toString)
                        .collect(Collectors.toList());
        System.out.println(String.join("\n", ctCertificateDtoString));
        System.out.println("\n\n");

        final String string = scanner.nextLine().replaceAll(" ", "");
        final List<String> idsString = Arrays.asList(string.split(","));

        return idsString.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
    private Integer getEducationDocumentId(final Integer abiturientId) throws BadRequestException {
        System.out.println("\n\nEnter education document id. Available documents: \n");

        final List<String> ctCertificateDtoStrings
                = educationDocumentService.getByAbiturientId(abiturientId).stream()
                    .map(EducationDocumentDto::toString)
                    .collect(Collectors.toList());
        System.out.println(String.join("\n", ctCertificateDtoStrings));
        System.out.println("\n\n");

        return getIntId();
    }

    private List<String> getSpecialities() {
        System.out.println("\n\nEnter specialities (coma separated). Available values: " +
                Arrays.stream(Specialty.values())
                    .map(Specialty::getName)
                    .collect(Collectors.joining(", ")));

        return Arrays.asList(scanner.nextLine().replaceAll(" ", "").split(","));
    }

    @Override
    public void create() throws BadRequestException {
        final StatementCreateRequest.StatementCreateRequestBuilder statementCreateRequestBuilder
                = StatementCreateRequest.builder();

        System.out.println("Enter abiturient id");
        final int abiturientId = getIntId();

        statementCreateRequestBuilder
                .abiturientId(abiturientId)
                .certificateIdsList(getCtCertificatesList(abiturientId))
                .educationDocumentId(getEducationDocumentId(abiturientId))
                .specialities(getSpecialities());

        statementService.create(statementCreateRequestBuilder.build());
    }

    @Override
    public void showList() {
        System.out.println("\n");
        List<StatementDto> list = statementService.getAll();

        if (list.isEmpty()) {
            System.out.println("There is not data :C");
            return;

        }

        list.stream()
                .sorted()
                .forEach(dto -> System.out.println(dto.toString() + "\n\n"));
    }

    @Override
    public void update() throws BadRequestException {
        System.out.println("Enter id");
        final int statementId = getIntId();

        final StatementDto currentState = statementService.getById(statementId);

        StatementUpdateRequest.StatementUpdateRequestBuilder builder = StatementUpdateRequest.builder()
                .certificateIdsList(getCtCertificatesList(currentState.getAbiturientId()))
                .educationDocumentId(getEducationDocumentId(currentState.getAbiturientId()))
                .specialities(getSpecialities());

        statementService.update(statementId, builder.build());
    }

    @Override
    public void delete() throws BadRequestException {
        System.out.println("Please, enter id");
        int id = getIntId();

        statementService.delete(id);
    }
}
