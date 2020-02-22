package men.brakh.enrollment.security.authentication.credentials;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.domain.BaseEntity;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "employee_credentials")
public class EmployeeCredentials implements BaseEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String password;
  private Integer employeeId;

}
