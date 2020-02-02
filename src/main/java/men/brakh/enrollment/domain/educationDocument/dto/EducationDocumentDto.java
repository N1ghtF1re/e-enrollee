package men.brakh.enrollment.domain.educationDocument.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EducationDocumentDto extends BaseEducationDocumentDto implements Comparable<EducationDocumentDto>{
    private static final transient long serialVersionUID = -6843655193820747639L;

    private Integer id;
    private Integer enrolleeId;
    private String enrolleeName;

    @Builder
    public EducationDocumentDto(final Double averageGrade,
                                final String educationalInstitution,
                                final String documentUniqueNumber,
                                final String documentType,
                                final Integer enrolleeId,
                                final String enrolleeName,
                                final Integer id) {
        super(averageGrade, educationalInstitution, documentUniqueNumber, documentType);
        this.enrolleeId = enrolleeId;
        this.enrolleeName = enrolleeName;
        this.id = id;
    }

    @Override
    public int compareTo(final EducationDocumentDto educationDocumentDto) {
        return (getEducationalInstitution() + enrolleeName)
                .compareTo(educationDocumentDto.getEducationalInstitution() + educationDocumentDto.enrolleeName);
    }

    @Override
    public String toString() {
        return String.format("%d | %-40s | %-20s | %-20s | %.1f / 10 | %-50s",
                id,
                getEducationalInstitution(),
                getDocumentType(),
                getDocumentUniqueNumber(),
                getAverageGrade(),
                getEnrolleeName());
    }
}
