package men.brakh.enrollment.model.ctCertificate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.Dto;

@NoArgsConstructor
@Data
@AllArgsConstructor
public abstract class BaseCtCertificateDto implements Dto {

    private static transient final long serialVersionUID = -6324392761831970655L;

    private Integer ctPoints;
    private String certificateIdentifier;
    private String certificateNumber;
    private String subject;
    private Integer year;
}
