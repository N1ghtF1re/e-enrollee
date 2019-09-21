package men.brakh.abiturient.model.ctCertificate;

import lombok.*;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.ParentAware;
import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.validation.NotFutureYear;

import javax.validation.constraints.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CtCertificate implements BaseEntity<Integer>, ParentAware<Integer> {
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
    private Abiturient abiturient;

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
        return abiturient == null ? null : abiturient.getId();
    }
}
