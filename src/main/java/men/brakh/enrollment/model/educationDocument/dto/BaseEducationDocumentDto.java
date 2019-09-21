package men.brakh.enrollment.model.educationDocument.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.Dto;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class BaseEducationDocumentDto implements Dto {
    private static final transient long serialVersionUID = 1831119144133343444L;

    private Double averageGrade;
    private String educationalInstitution;
    private String documentUniqueNumber;
    private String documentType;
}
