package men.brakh.enrollment.model.enrollee.dto;

import lombok.*;
import men.brakh.enrollment.model.UpdateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnrolleeUpdateRequest extends BaseEnrolleeDto implements UpdateDto {
    private static final transient long serialVersionUID = 4681559312328721409L;

    private int id;

    @Builder
    public EnrolleeUpdateRequest(final String firstName,
                                   final String lastName,
                                   final String middleName,
                                   final String birthDate,
                                   final int id) {
        super(firstName, lastName, middleName, birthDate);
        this.id = id;
    }
}
