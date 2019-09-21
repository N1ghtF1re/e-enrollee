package men.brakh.enrollment.controller.impl;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.controller.ConsoleCRUDController;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.enrollee.service.EnrolleeService;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.service.CtCertificateService;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.model.educationDocument.service.EducationDocumentService;
import men.brakh.enrollment.model.specialty.Specialty;
import men.brakh.enrollment.model.universityApplication.UniversityApplicationType;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationCreateRequest;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationDto;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationUpdateRequest;
import men.brakh.enrollment.model.universityApplication.service.UniversityApplicationService;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UniversityApplicationConsoleCRUDController implements ConsoleCRUDController {
    private final Scanner scanner = new Scanner(System.in);
    private final UniversityApplicationService universityApplicationService = Config.universityApplicationService;
    private final EnrolleeService enrolleeService = Config.enrolleeService;
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

    private List<Integer> getCtCertificatesList(final Integer enrolleeId) {
        System.out.println("\n\nEnter ct certificates ids (coma separated). Available certificates: \n");

        final List<String> ctCertificateDtoString
                = ctCertificateService.getByEnrolleeId(enrolleeId)
                        .stream()
                        .map(CtCertificateDto::toString)
                        .collect(Collectors.toList());
        System.out.println(String.join("\n", ctCertificateDtoString));
        System.out.println("\n\n");

        final String string = scanner.nextLine().replaceAll(" ", "");
        final List<String> idsString = Arrays.asList(string.split(","));

        return idsString.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
    private Integer getEducationDocumentId(final Integer enrolleeId) throws BadRequestException {
        System.out.println("\n\nEnter education document id. Available documents: \n");

        final List<String> ctCertificateDtoStrings
                = educationDocumentService.getByEnrolleeId(enrolleeId).stream()
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

    private String getApplicationType() throws BadRequestException {
        System.out.println("Please, select application type: (And enter number of type)");

        UniversityApplicationType[] types = UniversityApplicationType.values();

        for (int i = 0; i < types.length; i++) {
            System.out.println((i+1) + " - " + types[i].getDescription());
        }

        int index = getIntId();

        if (index <= 0 || index > types.length) {
            throw new BadRequestException("Invalid index");
        }

        return types[index - 1].toString();
    }

    @Override
    public void create() throws BadRequestException {
        final UniversityApplicationCreateRequest.UniversityApplicationCreateRequestBuilder universityApplicationCreateRequestBuilder
                = UniversityApplicationCreateRequest.builder();

        System.out.println("Enter enrollee id");
        final int enrolleeId = getIntId();

        universityApplicationCreateRequestBuilder
                .enrolleeId(enrolleeId)
                .certificateIdsList(getCtCertificatesList(enrolleeId))
                .educationDocumentId(getEducationDocumentId(enrolleeId))
                .type(getApplicationType())
                .specialities(getSpecialities());

        universityApplicationService.create(universityApplicationCreateRequestBuilder.build());
    }

    @Override
    public void showList() {
        System.out.println("\n");
        List<UniversityApplicationDto> list = universityApplicationService.getAll();

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
        final int universityApplicationId = getIntId();

        final UniversityApplicationDto currentState = universityApplicationService.getById(universityApplicationId);

        UniversityApplicationUpdateRequest.UniversityApplicationUpdateRequestBuilder builder = UniversityApplicationUpdateRequest.builder()
                .certificateIdsList(getCtCertificatesList(currentState.getEnrolleeId()))
                .educationDocumentId(getEducationDocumentId(currentState.getEnrolleeId()))
                .specialities(getSpecialities());

        universityApplicationService.update(universityApplicationId, builder.build());
    }

    @Override
    public void delete() throws BadRequestException {
        System.out.println("Please, enter id");
        int id = getIntId();

        universityApplicationService.delete(id);
    }
}
