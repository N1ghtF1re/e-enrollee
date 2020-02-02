package men.brakh.enrollment.domain.employee.dto;

import lombok.*;
import men.brakh.enrollment.domain.UpdateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeUpdateReuqest extends BaseEmployeeDto implements UpdateDto {
  private static final transient long serialVersionUID = 51511123142600501L;
  private int id;

  @Builder
  public EmployeeUpdateReuqest(final String firstName, final String lastName, final String middleName, final String role, final String username, final int id) {
    super(firstName, lastName, middleName, role, username);
    this.id = id;
  }

}
