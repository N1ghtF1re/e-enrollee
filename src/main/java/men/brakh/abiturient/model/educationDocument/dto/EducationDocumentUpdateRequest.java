package men.brakh.abiturient.model.educationDocument.dto;

import lombok.*;
import men.brakh.abiturient.model.UpdateDto;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EducationDocumentUpdateRequest extends BaseEducationDocumentDto implements UpdateDto {
    private static final transient long serialVersionUID = 9084745240660668063L;

    @Builder
    public EducationDocumentUpdateRequest(final Double averageGrade,
                                          final String educationalInstitution,
                                          final String documentUniqueNumber,
                                          final String documentType) {
        super(averageGrade, educationalInstitution, documentUniqueNumber, documentType);
    }
}
