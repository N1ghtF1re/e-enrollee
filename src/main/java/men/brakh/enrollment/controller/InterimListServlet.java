package men.brakh.enrollment.controller;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.model.interimLists.service.InterimListsService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/interim-list")
public class InterimListServlet extends HttpServlet {
  private final InterimListsService interimListsService = Config.interimListsService;

  @Override
  protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/interim-list/list.jsp");
    request.setAttribute("list", interimListsService.getList());
    dispatcher.forward(request, response);
  }
}
