package men.brakh.abiturient.model.statement.mapping;

import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.mapping.CtCertificateEntityPresenter;
import men.brakh.abiturient.model.educationDocument.dto.EducationDocumentDto;
import men.brakh.abiturient.model.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.abiturient.model.specialty.Specialty;
import men.brakh.abiturient.model.statement.Statement;
import men.brakh.abiturient.model.statement.dto.StatementDto;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class StatementEntityPresenter implements EntityPresenter<Statement, StatementDto> {
    private final ModelMapper modelMapper;
    private final DateFormat dateFormat;
    private final CtCertificateEntityPresenter ctCertificateEntityPresenter;
    private final EducationDocumentEntityPresenter educationDocumentEntityPresenter;

    public StatementEntityPresenter(final ModelMapper modelMapper,
                                    final DateFormat dateFormat,
                                    final CtCertificateEntityPresenter ctCertificateEntityPresenter,
                                    final EducationDocumentEntityPresenter educationDocumentEntityPresenter) {
        this.modelMapper = modelMapper;
        this.dateFormat = dateFormat;
        this.ctCertificateEntityPresenter = ctCertificateEntityPresenter;
        this.educationDocumentEntityPresenter = educationDocumentEntityPresenter;
    }


    @Override
    public StatementDto mapToDto(final Statement entity,
                                 final Class<? extends StatementDto> dtoClass) {
        StatementDto dto = modelMapper.map(entity, dtoClass);

        dto.setAbiturientId(entity.getAbiturient().getId());
        dto.setAbiturientName(entity.getAbiturient().getFullName());
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
    public List<StatementDto> mapListToDto(final List<Statement> entities,
                                           final Class<? extends StatementDto> dtoClass) {
        return entities.stream()
                .map(statement -> mapToDto(statement, dtoClass))
                .collect(Collectors.toList());
    }
}
