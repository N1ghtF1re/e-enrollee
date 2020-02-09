package men.brakh.enrollment.domain.ctCertificate.dto;

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

    @Override
    public int compareTo(final CtCertificateDto ctCertificateDto) {
        return this.enrolleeName.compareTo(ctCertificateDto.enrolleeName);
    }

    @Builder
    public CtCertificateDto(final Integer ctPoints, final String certificateIdentifier, final String certificateNumber, final String subject, final Integer year, final Integer enrolleeId, final Integer id, final String enrolleeName) {
        super(ctPoints, certificateIdentifier, certificateNumber, subject, year, enrolleeId);
        this.id = id;
        this.enrolleeName = enrolleeName;
    }
}
