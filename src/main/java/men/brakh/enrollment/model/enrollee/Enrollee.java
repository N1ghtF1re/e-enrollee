package men.brakh.enrollment.model.enrollee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.BaseEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Enrollee implements BaseEntity<Integer> {
    private static final transient String NAME_REGEX = "[A-ZА-Я][a-zа-я]*";
    private Integer id;

    @NotNull
    @Pattern(regexp = NAME_REGEX)
    private String firstName;

    @NotNull
    @Pattern(regexp = NAME_REGEX)
    private String lastName;

    @Pattern(regexp = NAME_REGEX)
    private String middleName;

    @NotNull(message = "Birth Date hasn't set or incorrect")
    private Date birthDate;


    @Override
    public Enrollee clone() {
        try {
            return (Enrollee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

}
