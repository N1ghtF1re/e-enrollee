package men.brakh.enrollment.model.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.BaseEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Employee implements BaseEntity<Integer> {
  private static final transient long serialVersionUID = -7547103123142600501L;
  private static final transient String NAME_REGEX = "[A-ZА-Я][a-zа-я]*";

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
