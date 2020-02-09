package men.brakh.enrollment.domain.ctCertificate.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import men.brakh.enrollment.domain.UpdateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CtCertificateUpdateRequest extends BaseCtCertificateDto implements UpdateDto {
    private static final transient long serialVersionUID = 5586183491047715483L;

}
