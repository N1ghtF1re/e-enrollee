package men.brakh.abiturient.model.abiturient.dto;

import lombok.*;
import men.brakh.abiturient.model.CreateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AbiturientCreateRequest extends BaseAbiturientDto implements CreateDto {

    private static final transient long serialVersionUID = -3290057376138021456L;

    @Builder
    public AbiturientCreateRequest(final String firstName,
                                   final String lastName,
                                   final String middleName,
                                   final String birthDate) {
        super(firstName, lastName, middleName, birthDate);
    }
}
