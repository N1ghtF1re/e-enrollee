package men.brakh.enrollment.domain.enrollee.dto;

import java.util.Date;
import lombok.*;
import men.brakh.enrollment.domain.CreateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnrolleeCreateRequest extends BaseEnrolleeDto implements CreateDto {

    private static final transient long serialVersionUID = -3290057376138021456L;

    @Builder
    public EnrolleeCreateRequest(final String firstName,
                                   final String lastName,
                                   final String middleName,
                                   final Date birthDate) {
        super(firstName, lastName, middleName, birthDate);
    }
}
