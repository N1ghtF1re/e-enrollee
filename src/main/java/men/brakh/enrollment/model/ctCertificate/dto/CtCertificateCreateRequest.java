package men.brakh.enrollment.model.ctCertificate.dto;

import lombok.*;
import men.brakh.enrollment.model.CreateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CtCertificateCreateRequest extends BaseCtCertificateDto implements CreateDto {
    private static transient final long serialVersionUID = 8812001052959020042L;

    private Integer enrolleeId;

    @Builder
    public CtCertificateCreateRequest(final Integer ctPoints,
                                      final String certificateId,
                                      final String certificateNumber,
                                      final String subject,
                                      final Integer year,
                                      final Integer enrolleeId) {
        super(ctPoints, certificateId, certificateNumber, subject, year);
        this.enrolleeId = enrolleeId;
    }
}
