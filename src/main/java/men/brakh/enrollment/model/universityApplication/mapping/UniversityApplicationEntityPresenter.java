package men.brakh.enrollment.model.universityApplication.mapping;

import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.mapping.CtCertificateEntityPresenter;
import men.brakh.enrollment.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.model.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.enrollment.model.specialty.Specialty;
import men.brakh.enrollment.model.universityApplication.UniversityApplication;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationDto;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class UniversityApplicationEntityPresenter implements EntityPresenter<UniversityApplication, UniversityApplicationDto> {
    private final ModelMapper modelMapper;
    private final DateFormat dateFormat;
    private final CtCertificateEntityPresenter ctCertificateEntityPresenter;
    private final EducationDocumentEntityPresenter educationDocumentEntityPresenter;

    public UniversityApplicationEntityPresenter(final ModelMapper modelMapper,
                                    final DateFormat dateFormat,
                                    final CtCertificateEntityPresenter ctCertificateEntityPresenter,
                                    final EducationDocumentEntityPresenter educationDocumentEntityPresenter) {
        this.modelMapper = modelMapper;
        this.dateFormat = dateFormat;
        this.ctCertificateEntityPresenter = ctCertificateEntityPresenter;
        this.educationDocumentEntityPresenter = educationDocumentEntityPresenter;
    }


    @Override
    public UniversityApplicationDto mapToDto(final UniversityApplication entity,
                                 final Class<? extends UniversityApplicationDto> dtoClass) {
        UniversityApplicationDto dto = modelMapper.map(entity, dtoClass);

        dto.setEnrolleeId(entity.getEnrollee().getId());
        dto.setEnrolleeName(entity.getEnrollee().getFullName());
        dto.setSpecialities(
                entity.getSpecialties()
                    .stream()
                    .map(Specialty::getName)
                    .collect(Collectors.toList())
        );
        dto.setId(entity.getId());
        dto.setCertificates(
                ctCertificateEntityPresenter.mapListToDto(
                        entity.getCertificates(), CtCertificateDto.class
                )
        );

        dto.setEducationDocument(
                educationDocumentEntityPresenter.mapToDto(
                        entity.getEducationDocument(), EducationDocumentDto.class
                )
        );
        dto.setDate(dateFormat.format(entity.getDate()));


        return dto;
    }

    @Override
    public List<UniversityApplicationDto> mapListToDto(final List<UniversityApplication> entities,
                                           final Class<? extends UniversityApplicationDto> dtoClass) {
        return entities.stream()
                .map(universityApplication -> mapToDto(universityApplication, dtoClass))
                .collect(Collectors.toList());
    }
}
