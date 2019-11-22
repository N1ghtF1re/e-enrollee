package men.brakh.enrollment.model.universityApplication.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import men.brakh.enrollment.model.Dto;
import men.brakh.enrollment.model.ctCertificate.dto.BaseCtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.model.universityApplication.EducationType;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class UniversityApplicationDto implements Dto, Comparable<UniversityApplicationDto> {
    private static final transient long serialVersionUID = -4485156198919209510L;

    private Integer id;

    private String enrolleeName;

    private Integer enrolleeId;

    private List<CtCertificateDto> certificates;

    private List<String> specialities;

    private EducationDocumentDto educationDocument;

    private String date;

    private String type;

    @Builder
    public UniversityApplicationDto(final Integer id,
                        final String enrolleeName,
                        final Integer enrolleeId,
                        final List<CtCertificateDto> certificates,
                        final List<String> specialities,
                        final EducationDocumentDto educationDocument,
                        final String date,
                        final String type) {
        this.id = id;
        this.enrolleeName = enrolleeName;
        this.enrolleeId = enrolleeId;
        this.certificates = certificates;
        this.specialities = specialities;
        this.educationDocument = educationDocument;
        this.date = date;
        this.type = type;
    }

    public int getTotalPoints() {
        final int ctTotalPoints = certificates.stream()
            .map(BaseCtCertificateDto::getCtPoints)
            .mapToInt(Integer::intValue)
            .sum();

        return (int) Math.round(ctTotalPoints + educationDocument.getAverageGrade() * 10);
    }

    @Override
    public int compareTo(final UniversityApplicationDto universityApplicationDto) {
        return this.enrolleeName.compareTo(universityApplicationDto.enrolleeName);
    }

    @Override
    public String toString() {


        return "APPLICATION #" + id
                + "\nTYPE: " + EducationType.valueOf(type).getDescription()
                + "\nENROLLEE: " + enrolleeName + " [" + enrolleeId + "]\n"
                + "\nTOTAL POINTS: " + getTotalPoints()
                + "\nCERTIFICATES: \n"
                + certificates.stream().map(CtCertificateDto::toString).collect(Collectors.joining("\n"))
                + "\nSPECIALITIES: " + String.join(", ", specialities)
                + "\nEDUCATION DOCUMENT: \n" + educationDocument
                +"\nDATE: " + date;
    }
}
