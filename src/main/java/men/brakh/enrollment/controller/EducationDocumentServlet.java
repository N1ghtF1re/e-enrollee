package men.brakh.enrollment.controller;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentCreateRequest;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentUpdateRequest;
import men.brakh.enrollment.model.enrollee.service.EnrolleeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/education-document/*", loadOnStartup = 1)
public class EducationDocumentServlet extends CrudServlet<EducationDocumentDto,
    EducationDocumentCreateRequest, EducationDocumentUpdateRequest, Integer> {

  private final EnrolleeService enrolleeService= Config.enrolleeService;

  public EducationDocumentServlet() {
    super(Config.educationDocumentService, "education-document");
  }

  @Override
  protected EducationDocumentCreateRequest extractCreateRequest(final HttpServletRequest request) {
    return EducationDocumentCreateRequest.builder()
        .enrolleeId(Integer.parseInt(request.getParameter("enrolleeId")))
        .averageGrade(Double.parseDouble(request.getParameter("averageGrade")))
        .documentType(request.getParameter("documentType"))
        .documentUniqueNumber(request.getParameter("documentUniqueNumber"))
        .educationalInstitution(request.getParameter("educationalInstitution"))
        .build();  }

  @Override
  protected EducationDocumentUpdateRequest extractUpdateRequest(final HttpServletRequest request) {
    return EducationDocumentUpdateRequest.builder()
        .averageGrade(Double.parseDouble(request.getParameter("averageGrade")))
        .documentType(request.getParameter("documentType"))
        .documentUniqueNumber(request.getParameter("documentUniqueNumber"))
        .educationalInstitution(request.getParameter("educationalInstitution"))
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
  protected Integer getId(final EducationDocumentDto dto) {
    return dto.getId();
  }

  @Override
  protected void getCreatePage(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("enrollees", enrolleeService.getAll());

    super.getCreatePage(request, response);
  }
}
