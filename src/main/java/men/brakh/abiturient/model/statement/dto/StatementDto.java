package men.brakh.abiturient.model.statement.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import men.brakh.abiturient.model.Dto;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class StatementDto implements Dto, Comparable<StatementDto> {
    private static final transient long serialVersionUID = -4485156198919209510L;

    private Integer id;

    private String abiturientName;
    private Integer abiturientId;

    private List<CtCertificateDto> certificates;

    private List<String> specialities;

    private EducationDocumentDto educationDocument;

    private String date;

    @Builder
    public StatementDto(final Integer id,
                        final String abiturientName,
                        final Integer abiturientId,
                        final List<CtCertificateDto> certificates,
                        final List<String> specialities,
                        final EducationDocumentDto educationDocument,
                        final String date) {
        this.id = id;
        this.abiturientName = abiturientName;
        this.abiturientId = abiturientId;
        this.certificates = certificates;
        this.specialities = specialities;
        this.educationDocument = educationDocument;
        this.date = date;
    }

    @Override
    public int compareTo(final StatementDto statementDto) {
        return this.abiturientName.compareTo(statementDto.abiturientName);
    }

    @Override
    public String toString() {
        return "STATEMENT #" + id
                + "\nABITURIENT: " + abiturientName + " [" + abiturientId + "]\n"
                + "CERTIFICATES: \n"
                + certificates.stream().map(CtCertificateDto::toString).collect(Collectors.joining("\n"))
                + "\nSPECIALITIES: " + String.join(", ", specialities)
                + "\nEDUCATION DOCUMENT: \n" + educationDocument
                +"\nDATE: " + date;
    }
}
