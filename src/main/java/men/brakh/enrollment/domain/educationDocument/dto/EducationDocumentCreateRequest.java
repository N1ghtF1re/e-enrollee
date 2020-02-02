package men.brakh.enrollment.domain.educationDocument.dto;

import lombok.*;
import men.brakh.enrollment.domain.CreateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EducationDocumentCreateRequest extends BaseEducationDocumentDto implements CreateDto {
    private static final transient long serialVersionUID = -1027993075584016687L;

    private Integer enrolleeId;

    @Builder
    public EducationDocumentCreateRequest(final Double averageGrade,
                                          final String educationalInstitution,
                                          final String documentUniqueNumber,
                                          final String documentType,
                                          final Integer enrolleeId) {

        super(averageGrade, educationalInstitution, documentUniqueNumber, documentType);
        this.enrolleeId = enrolleeId;
    }
}
