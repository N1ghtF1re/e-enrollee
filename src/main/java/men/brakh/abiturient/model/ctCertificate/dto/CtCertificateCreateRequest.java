package men.brakh.abiturient.model.ctCertificate.dto;

import lombok.*;
import men.brakh.abiturient.model.CreateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CtCertificateCreateRequest extends BaseCtCertificateDto implements CreateDto {
    private static transient final long serialVersionUID = 8812001052959020042L;

    private Integer abiturientId;

    @Builder
    public CtCertificateCreateRequest(final Integer ctPoints,
                                      final String certificateId,
                                      final String certificateNumber,
                                      final String subject,
                                      final Integer year,
                                      final Integer abiturientId) {
        super(ctPoints, certificateId, certificateNumber, subject, year);
        this.abiturientId = abiturientId;
    }
}
