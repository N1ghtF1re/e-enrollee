package men.brakh.abiturient.model.abiturient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.abiturient.model.BaseEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Abiturient implements BaseEntity<Integer> {
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
    public Abiturient clone() {
        try {
            return (Abiturient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

}
