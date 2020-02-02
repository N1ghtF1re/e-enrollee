package men.brakh.enrollment.domain.employee.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EmployeeDto extends BaseEmployeeDto implements Comparable<EmployeeDto> {
  private static final transient long serialVersionUID = -231231123142600501L;

  private int id;

  @Override
  public int compareTo(final EmployeeDto employeeDto) {
    String name1 = this.getFirstName() + this.getMiddleName() + this.getLastName();
    String name2 = employeeDto.getFirstName() + employeeDto.getMiddleName() + employeeDto.getLastName();
    return name1.compareTo(name2);
  }
}
