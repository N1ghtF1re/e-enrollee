package men.brakh.abiturient.model.statement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.abiturient.model.BaseEntity;
import men.brakh.abiturient.model.abiturient.Abiturient;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.educationDocument.EducationDocument;
import men.brakh.abiturient.model.specialty.Specialty;
import men.brakh.abiturient.validation.SubjectsInCerficiateEqualsSubjectsForSpecialities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@SubjectsInCerficiateEqualsSubjectsForSpecialities
public class Statement implements BaseEntity<Integer> {
    private Integer id;

    @NotNull
    private Abiturient abiturient;

    @Size(max = 3, min = 3)
    private List<CtCertificate> certificates;

    @NotNull
    @Size(min = 1)
    private List<Specialty> specialties;

    @NotNull
    private EducationDocument educationDocument;

    @Override
    public Statement clone() {
        try {
            return (Statement) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
