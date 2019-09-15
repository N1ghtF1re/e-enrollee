package men.brakh.abiturient.model.abiturient.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class AbiturientDto extends BaseAbiturientDto {
    private Integer id;


    @Builder
    public AbiturientDto(final String firstName,
                         final String lastName,
                         final String middleName,
                         final String birthDate,
                         final Integer id) {
        super(firstName, lastName, middleName, birthDate);
        this.id = id;
    }
}
