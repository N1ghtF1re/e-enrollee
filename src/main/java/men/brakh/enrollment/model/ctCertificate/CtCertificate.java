package men.brakh.enrollment.model.ctCertificate;

import lombok.*;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.ParentAware;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.validation.NotFutureYear;

import javax.validation.constraints.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CtCertificate implements BaseEntity<Integer>, ParentAware<Integer> {
    private static final transient long serialVersionUID = 1476394463348637646L;

    private Integer id;

    @Min(0)
    @Max(100)
    @NonNull
    private Integer ctPoints;

    @Pattern(regexp = "\\d{2}-\\d{3}-\\d", message = "Certificate id should be in format XX-XXX-X")
    @NotNull
    private String certificateIdentifier;

    @Size(min = 7, max = 7)
    @NotNull
    private String certificateNumber;

    @NonNull
    private Subject subject;

    @NotNull
    @Min(2010)
    @NotFutureYear
    private Integer year;

    @NotNull
    private Enrollee enrollee;

    @Override
    public CtCertificate clone() {
        try {
            return (CtCertificate) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getParentId() {
        return enrollee == null ? null : enrollee.getId();
    }
}
