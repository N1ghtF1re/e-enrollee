package men.brakh.enrollment.domain.employee.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import men.brakh.enrollment.domain.UpdateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeUpdateRequest extends BaseEmployeeDto implements UpdateDto {
  private static final transient long serialVersionUID = 51511123142600501L;
  private int id;

  @Builder
  public EmployeeUpdateRequest(final String firstName, final String lastName, final String middleName, final String role, final String username, final int id) {
    super(firstName, lastName, middleName, role, username);
    this.id = id;
  }
}
