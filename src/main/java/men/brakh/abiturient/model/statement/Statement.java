package men.brakh.abiturient.model.statement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.ParentAware;
import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.model.specialty.Specialty;
import men.brakh.abiturient.validation.OnlyInRecentYears;
import men.brakh.abiturient.validation.SingleAbutirient;
import men.brakh.abiturient.validation.SubjectsInCerficiateEqualsSubjectsForSpecialities;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder

@SubjectsInCerficiateEqualsSubjectsForSpecialities
@SingleAbutirient
public class Statement implements BaseEntity<Integer>, ParentAware<Integer> {
    private Integer id;

    @NotNull
    @Valid
    private Abiturient abiturient;

    @Size(max = 3, min = 3)
    @Valid
    @OnlyInRecentYears(years = 2, message = "You can only use certificates which were passed in recent 2 years")
    private List<CtCertificate> certificates;

    @NotNull
    @Size(min = 1)
    private List<Specialty> specialties;

    @NotNull
    @Valid
    private EducationDocument educationDocument;

    @NotNull
    private Date date;

    @Override
    public Statement clone() {
        try {
            return (Statement) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getParentId() {
        return abiturient == null ? null : abiturient.getId();
    }
}
