package men.brakh.enrollment.model.enrollee.dto;

import lombok.*;
import men.brakh.enrollment.model.CreateDto;

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
                                   final String birthDate) {
        super(firstName, lastName, middleName, birthDate);
    }
}
