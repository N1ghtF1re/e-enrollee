package men.brakh.enrollment.controller;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.employee.dto.EmployeeRegistrationRequest;
import men.brakh.enrollment.model.employee.service.EmployeeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration", loadOnStartup = 1)
public class RegistrationServlet extends HttpServlet {
  private final EmployeeService employeeService = Config.employeeService;

  @Override
  protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/registration.jsp");
    dispatcher.forward(request, response);
  }

  @Override
  protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    final String username = request.getParameter("username");
    final String firstName = request.getParameter("firstName");
    final String lastName = request.getParameter("lastName");
    final String middleName = request.getParameter("middleName");
    final String password = request.getParameter("password");

    final EmployeeRegistrationRequest registrationRequest = EmployeeRegistrationRequest.builder()
        .firstName(firstName)
        .lastName(lastName)
        .middleName(middleName)
        .username(username)
        .password(password)
        .build();

    try {
      employeeService.create(registrationRequest);
      final String redirectUrl = request.getRequestURI().replace("/registration", "/login");
      response.sendRedirect(redirectUrl);
    } catch (BadRequestException e) {
      request.setAttribute("error", e.getMessage());
      final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/registration.jsp");
      dispatcher.forward(request, response);
    }

  }
}
