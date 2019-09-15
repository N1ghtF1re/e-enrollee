package men.brakh.abiturient.model.ctCertificate.dto;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class CtCertificateDto extends BaseCtCertificateDto {
    private static transient final long serialVersionUID = -7584274006794325399L;

    private Integer id;
    private String abiturientName;
    private Integer abiturientId;

    @Builder
    public CtCertificateDto(final Integer ctPoints,
                            final String certificateId,
                            final String certificateNumber,
                            final String subject,
                            final Integer id,
                            final String year,
                            final String abiturientName,
                            final Integer abiturientId) {
        super(ctPoints, certificateId, certificateNumber, subject, year);
        this.id = id;
        this.abiturientId = abiturientId;
        this.abiturientName = abiturientName;
    }
}
