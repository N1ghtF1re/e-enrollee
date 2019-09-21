package men.brakh.enrollment.model.enrollee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.Dto;

@NoArgsConstructor
@Data
@AllArgsConstructor
public abstract class BaseEnrolleeDto implements Dto {
    private static final transient long serialVersionUID = 1158021080891320370L;

    private String firstName;
    private String lastName;
    private String middleName;
    private String birthDate;

}
