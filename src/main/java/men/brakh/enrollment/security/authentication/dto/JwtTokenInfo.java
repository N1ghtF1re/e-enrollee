package men.brakh.enrollment.security.authentication.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenInfo {
  private boolean valid;
}
