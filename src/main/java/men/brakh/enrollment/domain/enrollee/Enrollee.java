package men.brakh.enrollment.domain.enrollee;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.domain.BaseEntity;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "enrollee")
public class Enrollee implements BaseEntity<Integer> {
    private static final transient String NAME_REGEX = "[A-ZА-Я][a-zа-я]*";
    private static final transient long serialVersionUID = -7547104466942600501L;


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
    private String middleName;

    @NotNull(message = "Birth Date hasn't set or incorrect")
    private Date birthDate;

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

}
