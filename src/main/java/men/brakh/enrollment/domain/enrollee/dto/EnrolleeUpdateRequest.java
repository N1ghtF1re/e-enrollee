package men.brakh.enrollment.domain.enrollee.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import men.brakh.enrollment.domain.UpdateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EnrolleeUpdateRequest extends BaseEnrolleeDto implements UpdateDto {
    private static final transient long serialVersionUID = 4681559312328721409L;

    @Builder
    public EnrolleeUpdateRequest(final String firstName, final String lastName, final String middleName, final Date birthDate, final int id) {
        super(firstName, lastName, middleName, birthDate);
        this.id = id;
    }

    private int id;
}
