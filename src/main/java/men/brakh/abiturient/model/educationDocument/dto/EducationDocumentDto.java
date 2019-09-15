package men.brakh.abiturient.model.educationDocument.dto;

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
    private Integer abiturientId;
    private String abiturientName;

    @Builder
    public EducationDocumentDto(final Double averageGrade,
                                final String educationalInstitution,
                                final String documentUniqueNumber,
                                final String documentType,
                                final Integer abiturientId,
                                final String abiturientName,
                                final Integer id) {
        super(averageGrade, educationalInstitution, documentUniqueNumber, documentType);
        this.abiturientId = abiturientId;
        this.abiturientName = abiturientName;
        this.id = id;
    }

    @Override
    public int compareTo(final EducationDocumentDto educationDocumentDto) {
        return (getEducationalInstitution() + abiturientName)
                .compareTo(educationDocumentDto.getEducationalInstitution() + educationDocumentDto.abiturientName);
    }

    @Override
    public String toString() {
        return String.format("%d | %20s | %15s | %15s | %50s",
                id,
                getEducationalInstitution(),
                getDocumentType(),
                getDocumentUniqueNumber(),
                getAbiturientName());
    }
}
