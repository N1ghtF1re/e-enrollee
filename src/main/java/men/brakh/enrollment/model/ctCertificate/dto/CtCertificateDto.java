package men.brakh.enrollment.model.ctCertificate.dto;

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
    private String enrolleeName;
    private Integer enrolleeId;

    @Builder
    public CtCertificateDto(final Integer ctPoints,
                            final String certificateId,
                            final String certificateNumber,
                            final String subject,
                            final Integer id,
                            final Integer year,
                            final String enrolleeName,
                            final Integer enrolleeId) {
        super(ctPoints, certificateId, certificateNumber, subject, year);
        this.id = id;
        this.enrolleeId = enrolleeId;
        this.enrolleeName = enrolleeName;
    }

    @Override
    public String toString() {
        return String.format("%d | %-50s | %s | %s | %-20s | %d / 100 | %s year",
                id,
                getEnrolleeName(),
                getCertificateIdentifier(),
                getCertificateNumber(),
                getSubject(),
                getCtPoints(),
                getYear());
    }

    @Override
    public int compareTo(final CtCertificateDto ctCertificateDto) {
        return this.enrolleeName.compareTo(ctCertificateDto.enrolleeName);
    }
}
