package men.brakh.enrollment.domain.ctCertificate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.ParentAware;
import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.application.validation.NotFutureYear;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "ct_certificate")
public class CtCertificate implements BaseEntity<Integer>, ParentAware<Integer> {
    private static final transient long serialVersionUID = 1476394463348637646L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @NotNull
    @Min(2010)
    @NotFutureYear
    private Integer year;

    @NotNull
    @JoinColumn(name = "enrollee_id", referencedColumnName = "id")
    @ManyToOne
    private Enrollee enrollee;


    @Override
    public Integer getParentId() {
        return enrollee == null ? null : enrollee.getId();
    }
}
