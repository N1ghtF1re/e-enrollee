package men.brakh.enrollment.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenInfo {
  private boolean valid;
}
