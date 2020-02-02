package men.brakh.enrollment.domain.interimLists.dto;

import lombok.*;
import men.brakh.enrollment.domain.Dto;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterimListsDto implements Dto {
    private static final transient long serialVersionUID = -4316104068117155852L;

    private List<SpecialityInterimLists> specialityInterimLists;

    @Override
    public String toString() {
        return specialityInterimLists
                .stream()
                .map(SpecialityInterimLists::toString)
                .collect(Collectors.joining("\n\n"));
    }
}
