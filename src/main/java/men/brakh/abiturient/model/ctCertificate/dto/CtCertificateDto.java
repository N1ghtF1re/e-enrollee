package men.brakh.abiturient.model.ctCertificate.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CtCertificateDto extends BaseCtCertificateDto implements Comparable<CtCertificateDto> {
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

    @Override
    public String toString() {
        return String.format("%d | %50s | %s | %s | %15s | %d / 100 | %s year",
                id,
                getAbiturientName(),
                getCertificateIdentifier(),
                getCertificateNumber(),
                getSubject(),
                getCtPoints(),
                getYear());
    }

    @Override
    public int compareTo(final CtCertificateDto ctCertificateDto) {
        return this.abiturientName.compareTo(ctCertificateDto.abiturientName);
    }
}
