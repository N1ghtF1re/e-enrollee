package men.brakh.enrollment.model.employee.dto;

import lombok.*;
import men.brakh.enrollment.model.CreateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeRegistrationRequest extends BaseEmployeeDto implements CreateDto {
  private String password;

  @Builder
  public EmployeeRegistrationRequest(final String firstName, final String lastName, final String middleName, final String role, final String username, final String password) {
    super(firstName, lastName, middleName, role, username);
    this.password = password;
  }
}
