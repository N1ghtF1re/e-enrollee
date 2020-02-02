package men.brakh.enrollment.domain.interimLists.dto;

import lombok.*;
import men.brakh.enrollment.domain.enrollee.dto.EnrolleeDto;
import men.brakh.enrollment.domain.universityApplication.EducationType;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityInterimLists {
    private Integer passingScore;
    private String speciality;
    private EducationType applicationType;
    private List<EnrolleeDto> enrollees;

    @Override
    public String toString() {
        return speciality + " | " + applicationType.getDescription()
                + "\n------------------------------------------------------------------------------------"
                + "\nPassing score: "+ (passingScore == 0 ? "No competition" : passingScore)
                + "\n------------------------------------------------------------------------------------\n"
                + enrollees.stream()
                    .sorted()
                    .map(EnrolleeDto::toString)
                    .collect(Collectors.joining("\n"))
                + "\n------------------------------------------------------------------------------------";


    }
}
