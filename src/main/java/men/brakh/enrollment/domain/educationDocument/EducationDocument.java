package men.brakh.enrollment.domain.educationDocument;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.ParentAware;
import men.brakh.enrollment.domain.enrollee.Enrollee;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name="education_document")
public class EducationDocument implements BaseEntity<Integer>, ParentAware<Integer> {
    private static final transient long serialVersionUID = 7370268272749102129L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Min(0)
    @Max(10)
    @NotNull
    @Digits(integer = 2,fraction = 1)
    private Double averageGrade;

    @NotNull
    private String educationalInstitution;

    @NotNull
    private String documentUniqueNumber;

    @NotNull
    private String documentType;

    @NotNull
    @JoinColumn(name = "enrollee_id", referencedColumnName = "id")
    @ManyToOne
    private Enrollee enrollee;

    @Override
    public Integer getParentId() {
        return enrollee == null ? null : enrollee.getId();
    }
}
