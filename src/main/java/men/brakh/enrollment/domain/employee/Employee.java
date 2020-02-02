package men.brakh.enrollment.domain.employee;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.domain.BaseEntity;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "employee")
public class Employee implements BaseEntity<Integer> {
  private static final transient long serialVersionUID = -7547103123142600501L;
  private static final transient String NAME_REGEX = "[A-ZА-Я][a-zа-я]*";


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NotNull
  @Pattern(regexp = NAME_REGEX)
  private String firstName;

  @NotNull
  @Pattern(regexp = NAME_REGEX)
  private String lastName;

  @Pattern(regexp = NAME_REGEX)
  @NotNull
  private String middleName;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Role role;

  @NotNull
  @Size(min = 4)
  private String username;

  @Override
  public Employee clone() {
    try {
      return (Employee) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
}
