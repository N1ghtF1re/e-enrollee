package men.brakh.abiturient.model.educationDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.ParentAware;
import men.brakh.abiturient.model.abiturient.Abiturient;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class EducationDocument implements BaseEntity<Integer>, ParentAware<Integer> {
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
    private Abiturient abiturient;


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
        return abiturient == null ? null : abiturient.getId();
    }
}
