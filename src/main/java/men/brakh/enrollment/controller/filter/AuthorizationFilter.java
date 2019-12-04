package men.brakh.enrollment.controller.filter;

import men.brakh.enrollment.Config;
import men.brakh.enrollment.infrastructure.authorization.AuthorizationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
  private final AuthorizationManager authorizationManager = Config.authorizationManager;

  private final List<String> avaiablePages = Arrays.asList(
      "/login",
      "/registration",
      "/language"
  );

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {

  }

  private boolean pageIsAvalable(final String url) {
    return avaiablePages.stream()
        .anyMatch(url::endsWith);
  }

  @Override
  public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws IOException, ServletException {
    final HttpServletRequest request = (HttpServletRequest) req;
    final HttpServletResponse response = (HttpServletResponse) resp;

    final Integer authorizedId = authorizationManager.getSession(request.getSession().getId());


    if (authorizedId != null) {
      authorizationManager.authorize(authorizedId);
    } else if (!pageIsAvalable(request.getRequestURL().toString())) {
      response.sendRedirect(request.getServletContext().getContextPath() + "/login");
      return;
    }
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    authorizationManager.unAuthorize();
  }
}
