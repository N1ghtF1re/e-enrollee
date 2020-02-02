package men.brakh.enrollment.domain.universityApplication.dto;

import lombok.*;
import men.brakh.enrollment.domain.UpdateDto;

import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class UniversityApplicationUpdateRequest implements UpdateDto {
    private static final transient long serialVersionUID = -2686346733925309873L;

    private List<Integer> certificateIdsList;
    private List<String> specialities;
    private Integer educationDocumentId;

    @Builder
    public UniversityApplicationUpdateRequest(final List<Integer> certificateIdsList,
                                  final List<String> specialities,
                                  final Integer educationDocumentId) {
        this.certificateIdsList = certificateIdsList;
        this.specialities = specialities;
        this.educationDocumentId = educationDocumentId;
    }
}
