package men.brakh.enrollment.domain.universityApplication;

import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.domain.BaseEntity;
import men.brakh.enrollment.domain.ParentAware;
import men.brakh.enrollment.domain.ctCertificate.CtCertificate;
import men.brakh.enrollment.domain.educationDocument.EducationDocument;
import men.brakh.enrollment.domain.enrollee.Enrollee;
import men.brakh.enrollment.domain.specialty.Specialty;
import men.brakh.enrollment.application.validation.OnlyInRecentYears;
import men.brakh.enrollment.application.validation.SingleEnrollee;
import men.brakh.enrollment.application.validation.SubjectsInCerficiateEqualsSubjectsForSpecialities;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@SubjectsInCerficiateEqualsSubjectsForSpecialities
@SingleEnrollee
@Entity(name = "university_application")
public class UniversityApplication implements BaseEntity<Integer>, ParentAware<Integer> {
    private static final transient long serialVersionUID = -1421742815469953132L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @JoinColumn(name = "enrollee_id", referencedColumnName = "id")
    @ManyToOne
    private Enrollee enrollee;

    @Size(max = 3, min = 3)
    @Valid
    @JoinTable(
        name = "university_application_certificates",
        joinColumns = {@JoinColumn(name = "university_application_id")},
        inverseJoinColumns = {@JoinColumn(name = "certificates_id")}
    )
    @ManyToMany
    @OnlyInRecentYears(years = 2, message = "You can only use certificates which were passed in recent 2 years")
    private List<CtCertificate> certificates;

    @NotNull
    @Size(min = 1)
    @ElementCollection(targetClass=Specialty.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="university_application_specialties")
    private List<Specialty> specialties;

    @NotNull
    @JoinColumn(name = "educationDocument_id", referencedColumnName = "id")
    private EducationDocument educationDocument;

    @NotNull
    private Date date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EducationType type;


    @Override
    public Integer getParentId() {
        return enrollee == null ? null : enrollee.getId();
    }

    public int getScores() {
        return (int) (certificates.stream().map(CtCertificate::getCtPoints).mapToInt(Integer::intValue).sum()
                        + Math.round(educationDocument.getAverageGrade() * 10));
    }
}
