package men.brakh.enrollment.model.universityApplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.BaseEntity;
import men.brakh.enrollment.model.ParentAware;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.specialty.Specialty;
import men.brakh.enrollment.validation.OnlyInRecentYears;
import men.brakh.enrollment.validation.SingleEnrollee;
import men.brakh.enrollment.validation.SubjectsInCerficiateEqualsSubjectsForSpecialities;

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
@SingleEnrollee
public class UniversityApplication implements BaseEntity<Integer>, ParentAware<Integer> {
    private static final transient long serialVersionUID = -1421742815469953132L;

    private Integer id;

    @NotNull
    @Valid
    private Enrollee enrollee;

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

    @NotNull
    private EducationType type;

    @Override
    public UniversityApplication clone() {
        try {
            return (UniversityApplication) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getParentId() {
        return enrollee == null ? null : enrollee.getId();
    }

    public int getScores() {
        return (int) (certificates.stream().map(CtCertificate::getCtPoints).mapToInt(Integer::intValue).sum()
                        + Math.round(educationDocument.getAverageGrade() * 10));
    }
}
