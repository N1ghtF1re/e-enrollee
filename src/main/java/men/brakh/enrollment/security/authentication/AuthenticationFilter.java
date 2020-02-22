package men.brakh.enrollment.security.authentication;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import men.brakh.enrollment.exception.UnauthorizedException;
import men.brakh.enrollment.security.authentication.credentials.UserDetailsServiceImpl;
import men.brakh.enrollment.security.authentication.jwt.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
  private static final String HEADER_NAME = "Authorization";

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public AuthenticationFilter(final JwtService jwtService,
                              final UserDetailsServiceImpl userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final FilterChain chain
  ) throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader(HEADER_NAME);
    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = jwtService.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        throw new UnauthorizedException("Unable to get JWT Token", e);
      } catch (ExpiredJwtException e) {
        throw new UnauthorizedException("JWT Token has expired", e);
      }
    } else {
      logger.warn("JWT token is not found");
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

      if (jwtService.validateToken(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
            = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }

    try {
      chain.doFilter(request, response);
    }  finally {
      SecurityContextHolder.getContext().setAuthentication(null);
    }
  }
}
