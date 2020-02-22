package men.brakh.enrollment.security;

import men.brakh.enrollment.security.authentication.AuthenticationFilter;
import men.brakh.enrollment.security.authentication.credentials.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService jwtUserDetailsService;
  private final AuthenticationFilter authenticationFilter;
  private final AuthenticationEntryPoint authenticationEntryPoint;

  public WebSecurityConfig(final UserDetailsServiceImpl jwtUserDetailsService,
                           final AuthenticationFilter authenticationFilter,
                           final AuthenticationEntryPoint authenticationEntryPoint) {
    this.jwtUserDetailsService = jwtUserDetailsService;
    this.authenticationFilter = authenticationFilter;
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(jwtUserDetailsService)
        .passwordEncoder(passwordEncoder());
  }
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf()
          .disable()
        .authorizeRequests()
          .antMatchers(HttpMethod.OPTIONS,"/**")
            .permitAll()//allow CORS option calls
          .antMatchers("/api/v1/auth", "/api/v1/registration", "/api/v1/token/check")
            .permitAll()
          .anyRequest()
            .authenticated()
        .and()
          .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

    httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer(
      @Value("${security.cors.origins}") final String corsOrigins
  ) {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins(corsOrigins)
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowCredentials(true);
      }
    };
  }
}