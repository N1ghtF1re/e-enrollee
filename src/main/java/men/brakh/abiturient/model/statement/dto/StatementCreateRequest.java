package men.brakh.abiturient.model.statement.dto;

import lombok.*;
import men.brakh.abiturient.model.CreateDto;

import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class StatementCreateRequest implements CreateDto {
    private static final transient long serialVersionUID = 5559332672141733424L;

    private Integer abiturientId;
    private List<Integer> certificateIdsList;
    private List<String> specialities;
    private Integer educationDocumentId;

    @Builder
    public StatementCreateRequest(final Integer abiturientId,
                                  final List<Integer> certificateIdsList,
                                  final List<String> specialities,
                                  final Integer educationDocumentId) {
        this.abiturientId = abiturientId;
        this.certificateIdsList = certificateIdsList;
        this.specialities = specialities;
        this.educationDocumentId = educationDocumentId;
    }
}
