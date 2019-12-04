package men.brakh.enrollment.model.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.Dto;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class BaseEmployeeDto implements Dto {
  private String firstName;
  private String lastName;
  private String middleName;
  private String role;
  private String username;
}
