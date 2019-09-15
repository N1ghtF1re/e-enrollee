package men.brakh.abiturient.model.abiturient.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AbiturientDto extends BaseAbiturientDto implements Comparable<AbiturientDto> {
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


    @Override
    public String toString() {
        return String.format("%d | %15s | %15s | %15s | %s",
                id,
                getFirstName(),
                getLastName(),
                getMiddleName(),
                getBirthDate()
                );
    }

    @Override
    public int compareTo(final AbiturientDto abiturientDto) {
        String name1 = this.getFirstName() + this.getMiddleName() + this.getLastName();
        String name2 = abiturientDto.getFirstName() + abiturientDto.getMiddleName() + abiturientDto.getLastName();
        return name1.compareTo(name2);
    }
}
