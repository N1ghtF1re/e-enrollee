package men.brakh.enrollment.domain.ctCertificate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import men.brakh.enrollment.domain.CreateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CtCertificateCreateRequest extends BaseCtCertificateDto implements CreateDto {
    private static transient final long serialVersionUID = 8812001052959020042L;

    @Builder
    public CtCertificateCreateRequest(final Integer ctPoints, final String certificateIdentifier, final String certificateNumber, final String subject, final Integer year, final Integer enrolleeId) {
        super(ctPoints, certificateIdentifier, certificateNumber, subject, year, enrolleeId);
    }
}
