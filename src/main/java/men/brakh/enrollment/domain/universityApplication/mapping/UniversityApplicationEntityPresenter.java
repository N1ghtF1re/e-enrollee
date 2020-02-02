package men.brakh.enrollment.domain.universityApplication.mapping;

import java.util.List;
import java.util.stream.Collectors;
import men.brakh.enrollment.domain.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.domain.ctCertificate.mapping.CtCertificateEntityPresenter;
import men.brakh.enrollment.domain.educationDocument.dto.EducationDocumentDto;
import men.brakh.enrollment.domain.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.enrollment.domain.specialty.Specialty;
import men.brakh.enrollment.domain.universityApplication.UniversityApplication;
import men.brakh.enrollment.domain.universityApplication.dto.UniversityApplicationDto;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UniversityApplicationEntityPresenter implements EntityPresenter<UniversityApplication, UniversityApplicationDto> {
    private final ModelMapper modelMapper;
    private final CtCertificateEntityPresenter ctCertificateEntityPresenter;
    private final EducationDocumentEntityPresenter educationDocumentEntityPresenter;

    public UniversityApplicationEntityPresenter(final ModelMapper modelMapper,
                                    final CtCertificateEntityPresenter ctCertificateEntityPresenter,
                                    final EducationDocumentEntityPresenter educationDocumentEntityPresenter) {
        this.modelMapper = modelMapper;
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
