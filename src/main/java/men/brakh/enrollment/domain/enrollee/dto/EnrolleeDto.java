package men.brakh.enrollment.domain.enrollee.dto;

import java.util.Date;
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
    public EnrolleeDto(final String firstName, final String lastName, final String middleName, final Date birthDate, final Integer id) {
        super(firstName, lastName, middleName, birthDate);
        this.id = id;
    }

    @Override
    public int compareTo(final EnrolleeDto enrolleeDto) {
        String name1 = this.getFirstName() + this.getMiddleName() + this.getLastName();
        String name2 = enrolleeDto.getFirstName() + enrolleeDto.getMiddleName() + enrolleeDto.getLastName();
        return name1.compareTo(name2);
    }
}
