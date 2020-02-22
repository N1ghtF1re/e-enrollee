package men.brakh.enrollment.security.authentication.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Getter
@Setter
public class JwtSettings {

  private Integer expiration;

  private String tokenIssuer;

  private String tokenSigningKey;

}
