package men.brakh.abiturient.model.statement.dto;

import lombok.*;
import men.brakh.abiturient.model.UpdateDto;

import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class StatementUpdateRequest implements UpdateDto {
    private static final transient long serialVersionUID = -2686346733925309873L;

    private List<Integer> certificateIdsList;
    private List<String> specialities;
    private Integer educationDocumentId;

    @Builder
    public StatementUpdateRequest(final List<Integer> certificateIdsList,
                                  final List<String> specialities,
                                  final Integer educationDocumentId) {
        this.certificateIdsList = certificateIdsList;
        this.specialities = specialities;
        this.educationDocumentId = educationDocumentId;
    }
}
