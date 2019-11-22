package men.brakh.enrollment.controller;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.ctCertificate.service.CtCertificateService;
import men.brakh.enrollment.model.educationDocument.service.EducationDocumentService;
import men.brakh.enrollment.model.enrollee.service.EnrolleeService;
import men.brakh.enrollment.model.specialty.Specialty;
import men.brakh.enrollment.model.universityApplication.EducationType;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationCreateRequest;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationDto;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationUpdateRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/application/*", loadOnStartup = 1)
public class ApplicationServlet extends CrudServlet<UniversityApplicationDto,
    UniversityApplicationCreateRequest, UniversityApplicationUpdateRequest, Integer> {
  private final CtCertificateService ctCertificateService = Config.ctCertificateService;
  private final EnrolleeService enrolleeService = Config.enrolleeService;
  private final EducationDocumentService educationDocumentService = Config.educationDocumentService;

  public ApplicationServlet() {
    super(Config.universityApplicationService, "application");
  }

  @Override
  protected UniversityApplicationCreateRequest extractCreateRequest(final HttpServletRequest request) {
    final String[] specialities = request.getParameterValues("specialities");
    final String[] certificates = request.getParameterValues("certificateIdsList");
    final String educationDocumentId = request.getParameter("educationDocumentId");
    final String enrolleeId = request.getParameter("enrolleeId");

    return UniversityApplicationCreateRequest.builder()
        .specialities(specialities == null ? null : Arrays.asList(specialities))
        .type(request.getParameter("type"))
        .certificateIdsList(
            certificates == null ? null : Arrays.stream(certificates)
                .map(Integer::parseInt)
                .collect(Collectors.toList())
        )
        .educationDocumentId(educationDocumentId == null ? null : Integer.parseInt(educationDocumentId))
        .enrolleeId(enrolleeId == null ? null : Integer.parseInt(enrolleeId))
        .build();
  }

  @Override
  protected UniversityApplicationUpdateRequest extractUpdateRequest(final HttpServletRequest request) {
    final String[] specialities = request.getParameterValues("specialities");
    final String[] certificates = request.getParameterValues("certificateIdsList");
    final String educationDocumentId = request.getParameter("educationDocumentId");

    return UniversityApplicationUpdateRequest.builder()
        .specialities(specialities == null ? null : Arrays.asList(specialities))
        .certificateIdsList(
            certificates == null ? null : Arrays.stream(certificates)
                .map(Integer::parseInt)
                .collect(Collectors.toList())
        )
        .educationDocumentId(educationDocumentId == null ? null : Integer.parseInt(educationDocumentId))
        .build();
  }

  @Override
  protected Integer getId(final String stringId) {
    try {
      return Integer.parseInt(stringId);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  @Override
  protected Integer getId(final UniversityApplicationDto dto) {
    return dto.getId();
  }

  @Override
  protected void getCreatePage(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("enrollees", enrolleeService.getAll());

    final String idString = request.getParameter("enrolleeId");

    if (idString != null) {
      final Integer enrolleeId = Integer.parseInt(idString);
      request.setAttribute("types", EducationType.values());
      request.setAttribute("specialitiesList", Specialty.values());
      request.setAttribute("documents", educationDocumentService.getByEnrolleeId(enrolleeId));
      request.setAttribute("certificates", ctCertificateService.getByEnrolleeId(enrolleeId));
    }


    super.getCreatePage(request, response);
  }

  @Override
  protected void getEditEntityPage(final Integer id, final HttpServletRequest request, final HttpServletResponse response) throws BadRequestException, ServletException, IOException {
    final Integer enrolleeId = crudEntityService.getById(id).getEnrolleeId();
    request.setAttribute("types", EducationType.values());
    request.setAttribute("specialitiesList", Specialty.values());
    request.setAttribute("documents", educationDocumentService.getByEnrolleeId(enrolleeId));
    request.setAttribute("certificates", ctCertificateService.getByEnrolleeId(enrolleeId));

    super.getEditEntityPage(id, request, response);
  }
}
