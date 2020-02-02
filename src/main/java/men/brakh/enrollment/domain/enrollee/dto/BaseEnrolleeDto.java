package men.brakh.enrollment.domain.enrollee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.domain.Dto;

@NoArgsConstructor
@Data
@AllArgsConstructor
public abstract class BaseEnrolleeDto implements Dto {
    private static final transient long serialVersionUID = 1158021080891320370L;

    private String firstName;
    private String lastName;
    private String middleName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
}
