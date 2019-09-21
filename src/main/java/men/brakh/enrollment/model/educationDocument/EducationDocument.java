package men.brakh.enrollment.model.educationDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.ParentAware;
import men.brakh.enrollment.model.enrollee.Enrollee;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class EducationDocument implements BaseEntity<Integer>, ParentAware<Integer> {
    private static final transient long serialVersionUID = 7370268272749102129L;

    private Integer id;

    @Min(0)
    @Max(10)
    @NotNull
    private Double averageGrade;

    @NotNull
    private String educationalInstitution;

    @NotNull
    private String documentUniqueNumber;

    @NotNull
    private String documentType;

    @NotNull
    private Enrollee enrollee;


    @Override
    public EducationDocument clone() {
        try {
            return (EducationDocument) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getParentId() {
        return enrollee == null ? null : enrollee.getId();
    }
}
