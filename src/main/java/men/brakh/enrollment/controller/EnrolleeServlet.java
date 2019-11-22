package men.brakh.enrollment.controller;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.ctCertificate.service.CtCertificateService;
import men.brakh.enrollment.model.educationDocument.service.EducationDocumentService;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeCreateRequest;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.model.enrollee.dto.EnrolleeUpdateRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/enrollee/*", loadOnStartup = 1)
public class EnrolleeServlet
    extends CrudServlet<EnrolleeDto, EnrolleeCreateRequest, EnrolleeUpdateRequest, Integer> {
  private final CtCertificateService ctCertificateService = Config.ctCertificateService;
  private final EducationDocumentService educationDocumentService = Config.educationDocumentService;

  public EnrolleeServlet() {
    super(Config.enrolleeService, "enrollee");
  }

  @Override
  protected EnrolleeCreateRequest extractCreateRequest(final HttpServletRequest request) {
    return EnrolleeCreateRequest.builder()
        .firstName(request.getParameter("firstName"))
        .lastName(request.getParameter("lastName"))
        .middleName(request.getParameter("middleName"))
        .birthDate(request.getParameter("birthDate"))
        .build();
  }

  @Override
  protected EnrolleeUpdateRequest extractUpdateRequest(final HttpServletRequest request) {
    return EnrolleeUpdateRequest.builder()
        .firstName(request.getParameter("firstName"))
        .lastName(request.getParameter("lastName"))
        .middleName(request.getParameter("middleName"))
        .birthDate(request.getParameter("birthDate"))
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
  protected Integer getId(final EnrolleeDto dto) {
    return dto.getId();
  }

  @Override
  protected void getEntityPage(final Integer id, final HttpServletRequest request,
                               final HttpServletResponse response
  ) throws BadRequestException, ServletException, IOException {
    request.setAttribute("certificates", ctCertificateService.getByEnrolleeId(id));
    request.setAttribute("documents", educationDocumentService.getByEnrolleeId(id));
    super.getEntityPage(id, request, response);
  }
}
