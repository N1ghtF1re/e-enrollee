package men.brakh.enrollment.model.universityApplication.dto;

import lombok.*;
import men.brakh.enrollment.model.CreateDto;

import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class UniversityApplicationCreateRequest implements CreateDto {
    private static final transient long serialVersionUID = 5559332672141733424L;

    private Integer enrolleeId;
    private List<Integer> certificateIdsList;
    private List<String> specialities;
    private Integer educationDocumentId;

    @Builder
    public UniversityApplicationCreateRequest(final Integer enrolleeId,
                                  final List<Integer> certificateIdsList,
                                  final List<String> specialities,
                                  final Integer educationDocumentId) {
        this.enrolleeId = enrolleeId;
        this.certificateIdsList = certificateIdsList;
        this.specialities = specialities;
        this.educationDocumentId = educationDocumentId;
    }
}
