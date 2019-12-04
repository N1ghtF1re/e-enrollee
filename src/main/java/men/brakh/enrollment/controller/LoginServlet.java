package men.brakh.enrollment.controller;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.infrastructure.authorization.AuthorizationManager;
import men.brakh.enrollment.model.employee.service.EmployeeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
  private final EmployeeService employeeService = Config.employeeService;
  private final AuthorizationManager authorizationManager = Config.authorizationManager;

  @Override
  protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
    dispatcher.forward(request, response);
  }

  @Override
  protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    final String username = request.getParameter("username");
    final String password = request.getParameter("password");

    if (employeeService.isCredentialsCorrect(username, password)) {
      final Integer id = employeeService.getIdByUsername(username);
      authorizationManager.addSession(request.getSession().getId(), id);

      response.sendRedirect(request.getServletContext().getContextPath());
    } else {
      request.setAttribute("error", "Username or password is incorrect");
      final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
      dispatcher.forward(request, response);
    }
  }
}
