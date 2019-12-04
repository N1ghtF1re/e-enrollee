package men.brakh.enrollment.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/language")
public class LanguageServlet extends HttpServlet {
  @Override
  protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    final String redirectUrl = req.getParameter("redirect_url");
    final String language = req.getParameter("lang");

    resp.addCookie(new Cookie("lang", language));
    resp.sendRedirect(redirectUrl);
  }
}
