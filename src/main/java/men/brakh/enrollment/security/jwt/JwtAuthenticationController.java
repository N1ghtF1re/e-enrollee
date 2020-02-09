package men.brakh.enrollment.security.jwt;


import men.brakh.enrollment.exception.UnauthorizedException;
import men.brakh.enrollment.security.credentials.UserDetailsServiceImpl;
import men.brakh.enrollment.security.dto.AuthenticationRequest;
import men.brakh.enrollment.security.dto.AuthenticationResponse;
import men.brakh.enrollment.security.dto.JwtTokenInfo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationController(final AuthenticationManager authenticationManager,
                                     final JwtService jwtService,
                                     final UserDetailsServiceImpl userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }


  @PostMapping("/api/v1/auth")
  public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    tryAuthenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    final UserDetails userDetails = userDetailsService
        .loadUserByUsername(authenticationRequest.getUsername());

    final String token = jwtService.generateToken(userDetails);
    return new AuthenticationResponse(token);
  }

  @GetMapping("/api/v1/token/check")
  public @ResponseBody JwtTokenInfo checkToken(@RequestParam final String jwt) {
    boolean isTokenValid;

    try {
      isTokenValid = !jwtService.isTokenExpired(jwt);
    } catch (RuntimeException e) {
      isTokenValid = false;
    }

    return JwtTokenInfo.builder()
        .valid(isTokenValid)
        .build();
  }

  private void tryAuthenticate(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new UnauthorizedException("USER_DISABLED", e);
    } catch (RuntimeException e) {
      throw new UnauthorizedException("Invalid credentials", e);
    }
  }
}