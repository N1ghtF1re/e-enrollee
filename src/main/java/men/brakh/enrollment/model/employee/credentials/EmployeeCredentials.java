package men.brakh.enrollment.model.employee.credentials;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.BaseEntity;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class EmployeeCredentials implements BaseEntity<Integer> {
  private Integer id;
  private byte[] password;
  private byte[] salt;
  private Integer employeeId;

  @Override
  public EmployeeCredentials clone() {
    try {
      return (EmployeeCredentials) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
}
