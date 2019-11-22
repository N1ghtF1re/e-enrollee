package men.brakh.enrollment.controller;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.ctCertificate.Subject;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateUpdateRequest;
import men.brakh.enrollment.model.enrollee.service.EnrolleeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/ct-certificate/*", loadOnStartup = 1)
public class CtCertificateServlet extends CrudServlet<CtCertificateDto,
    CtCertificateCreateRequest, CtCertificateUpdateRequest, Integer> {

  private final EnrolleeService enrolleeService = Config.enrolleeService;

  public CtCertificateServlet() {
    super(Config.ctCertificateService, "ct-certificate");
  }

  @Override
  protected CtCertificateCreateRequest extractCreateRequest(final HttpServletRequest request) {
    return CtCertificateCreateRequest.builder()
        .certificateId(request.getParameter("certificateIdentifier"))
        .certificateNumber(request.getParameter("certificateNumber"))
        .ctPoints(Integer.parseInt(request.getParameter("ctPoints")))
        .subject(request.getParameter("subject"))
        .enrolleeId(Integer.parseInt(request.getParameter("enrolleeId")))
        .year(Integer.parseInt(request.getParameter("year")))
        .build();
  }

  @Override
  protected CtCertificateUpdateRequest extractUpdateRequest(final HttpServletRequest request) {
    return CtCertificateUpdateRequest.builder()
        .certificateId(request.getParameter("certificateIdentifier"))
        .certificateNumber(request.getParameter("certificateNumber"))
        .ctPoints(Integer.parseInt(request.getParameter("ctPoints")))
        .subject(request.getParameter("subject"))
        .year(Integer.parseInt(request.getParameter("year")))
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
  protected Integer getId(final CtCertificateDto dto) {
    return dto.getId();
  }

  @Override
  protected void getEditEntityPage(final Integer id, final HttpServletRequest request, final HttpServletResponse response) throws BadRequestException, ServletException, IOException {
    request.setAttribute("subjects", Subject.values());
    super.getEditEntityPage(id, request, response);
  }

  @Override
  protected void getCreatePage(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("subjects", Subject.values());
    request.setAttribute("enrollees", enrolleeService.getAll());
    super.getCreatePage(request, response);
  }
}
