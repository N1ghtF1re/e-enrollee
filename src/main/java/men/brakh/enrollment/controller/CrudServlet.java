package men.brakh.enrollment.controller;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.model.CreateDto;
import men.brakh.enrollment.model.Dto;
import men.brakh.enrollment.model.UpdateDto;
import men.brakh.enrollment.service.CRUDEntityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class CrudServlet<D extends Dto,
    C extends CreateDto,
    U extends UpdateDto,
    I> extends HttpServlet {
  protected final CRUDEntityService<D, C, U, I> crudEntityService;
  private final String basePath;

  protected CrudServlet(final CRUDEntityService crudEntityService, final String basePath) {
    this.crudEntityService = crudEntityService;
    this.basePath = basePath;
  }

  protected abstract C extractCreateRequest(final HttpServletRequest request);
  protected abstract U extractUpdateRequest(final HttpServletRequest request);
  protected abstract I getId(final String stringId);
  protected abstract I getId(final D dto);

  @Override
  protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    try {
      final C createRequest = extractCreateRequest(request);

      final D createdEntity = crudEntityService.create(createRequest);

      response.sendRedirect(request.getRequestURL() + "/" + getId(createdEntity));
    } catch (BadRequestException | NumberFormatException e) {
      request.setAttribute("error", e.getMessage().replaceAll("\n", "<\\br>"));
      request.getParameterMap()
        .forEach((name, value) -> {
          if (value.length != 0) {
            request.setAttribute(name, String.join(",", value));
          }
        });
      getCreatePage(request, response);
    }
  }

  @Override
  protected void doDelete(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    final String url = request.getRequestURL().toString();
    final String[] path = url.split("/");

    final I id = getId(path[path.length - 1]);

    try {
      crudEntityService.delete(id);
      final String redirectUrl = url.replace("/" + id, "/list");
      response.sendRedirect(redirectUrl);
    } catch (BadRequestException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void doPut(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    final String url = request.getRequestURL().toString();
    final String[] path = url.split("/");
    final I id = getId(path[path.length - 1]);

    try {
      final U updateRequest = extractUpdateRequest(request);

      crudEntityService.update(id, updateRequest);

      response.sendRedirect(request.getRequestURL().toString());
    } catch (BadRequestException | NumberFormatException e) {
      request.setAttribute("error", e.getMessage().replaceAll("\n", "<\\br>"));
      try {
        getEditEntityPage(id, request, response);
      } catch (BadRequestException ignored) {

      }
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    final String url = request.getRequestURL().toString();
    try {
      if (url.endsWith("list")) {
        getListPage(request, response);
      } else if (url.endsWith("create")) {
        getCreatePage(request, response);
      } else if (url.endsWith("edit")) {
        final String[] path = url.split("/");
        final I id = getId(path[path.length - 2]);

        getEditEntityPage(id, request, response);
      } else {
        final String[] path = url.split("/");
        final I id = getId(path[path.length - 1]);

        getEntityPage(id, request, response);
      }
    } catch (BadRequestException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getOutputStream().print("Not found");
    }
  }

  protected void getListPage(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {
    final List<D> list = crudEntityService.getAll();
    request.setAttribute("list", list);
    final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/" + basePath + "/list.jsp");
    dispatcher.forward(request, response);
  }

  protected void getCreatePage(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {
    final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/" + basePath + "/create.jsp");
    dispatcher.forward(request, response);
  }

  protected void getEntityPage(final I id, final HttpServletRequest request, final HttpServletResponse response)
      throws BadRequestException, ServletException, IOException {
    final D dto = crudEntityService.getById(id);
    request.setAttribute("entity", dto);
    final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/" + basePath + "/get.jsp");
    dispatcher.forward(request, response);
  }

  protected void getEditEntityPage(final I id, final HttpServletRequest request, final HttpServletResponse response) throws BadRequestException, ServletException, IOException {
    final D dto = crudEntityService.getById(id);
    request.setAttribute("entity", dto);
    final RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/" + basePath + "/update.jsp");
    dispatcher.forward(request, response);
  }

  @Override
  protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    final String method = req.getParameter("_method");
    if (method != null) {
      switch (method.toUpperCase()) {
        case "PUT":
          doPut(req, resp);
          return;
        case "DELETE":
          doDelete(req, resp);
          return;
      }
    }

    super.service(req, resp);
  }
}
