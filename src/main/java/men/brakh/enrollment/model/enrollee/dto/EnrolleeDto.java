package men.brakh.enrollment.model.enrollee.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EnrolleeDto extends BaseEnrolleeDto implements Comparable<EnrolleeDto> {
    private Integer id;


    @Builder
    public EnrolleeDto(final String firstName,
                         final String lastName,
                         final String middleName,
                         final String birthDate,
                         final Integer id) {
        super(firstName, lastName, middleName, birthDate);
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%d | %-20s | %-20s | %-20s | %s",
                id,
                getFirstName(),
                getLastName(),
                getMiddleName(),
                getBirthDate()
                );
    }

    @Override
    public int compareTo(final EnrolleeDto enrolleeDto) {
        String name1 = this.getFirstName() + this.getMiddleName() + this.getLastName();
        String name2 = enrolleeDto.getFirstName() + enrolleeDto.getMiddleName() + enrolleeDto.getLastName();
        return name1.compareTo(name2);
    }
}
