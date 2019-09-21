package men.brakh.abiturient.model.statement.mapping;

import men.brakh.abiturient.mapping.mapper.CreateDtoMapper;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.mapper.UpdateDtoMapper;
import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.repository.CtCertificateRepository;
import men.brakh.abiturient.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.abiturient.model.specialty.Specialty;
import men.brakh.abiturient.model.statement.Statement;
import men.brakh.abiturient.model.statement.dto.StatementCreateRequest;
import men.brakh.abiturient.model.statement.dto.StatementDto;
import men.brakh.abiturient.model.statement.dto.StatementUpdateRequest;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StatementDtoMapper implements DtoMapper<StatementDto, Statement>,
        CreateDtoMapper<StatementCreateRequest, Statement>, UpdateDtoMapper<StatementUpdateRequest, Statement> {

    private final ModelMapper modelMapper;
    private final AbiturientRepository abiturientRepository;
    private final CtCertificateRepository ctCertificateRepository;
    private final EducationDocumentRepository educationDocumentRepository;
    private final DateFormat dateFormat;

    public StatementDtoMapper(final ModelMapper modelMapper,
                              final DateFormat dateFormat,
                              final AbiturientRepository abiturientRepository,
                              final CtCertificateRepository ctCertificateRepository,
                              final EducationDocumentRepository educationDocumentRepository) {
        this.modelMapper = modelMapper;
        this.dateFormat = dateFormat;
        this.abiturientRepository = abiturientRepository;
        this.ctCertificateRepository = ctCertificateRepository;
        this.educationDocumentRepository = educationDocumentRepository;

        modelMapper.typeMap(StatementCreateRequest.class, Statement.class)
                .addMappings(mapping -> mapping.skip(Statement::setId));
        modelMapper.typeMap(StatementUpdateRequest.class, Statement.class)
                .addMappings(mapping -> mapping.skip(Statement::setId));
        modelMapper.typeMap(StatementDto.class, Statement.class)
                .addMappings(mapping -> mapping.skip(Statement::setCertificates))
                .addMappings(mapping -> mapping.skip(Statement::setDate));

    }

    private List<CtCertificate> mapCtCertificates(List<Integer> ids) {
        return ids
                .stream()
                .map(id -> ctCertificateRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
    }

    @Override
    public Statement mapToEntity(final StatementCreateRequest createRequest) {
        Statement statement = modelMapper.map(createRequest, Statement.class);

        statement.setAbiturient(abiturientRepository.findById(createRequest.getAbiturientId()).orElse(null));

        statement.setCertificates(mapCtCertificates(createRequest.getCertificateIdsList()));

        statement.setEducationDocument(educationDocumentRepository.findById(createRequest.getEducationDocumentId())
                .orElse(null));

        statement.setSpecialties(createRequest.getSpecialities()
                .stream()
                .map(Specialty::valueOf).collect(Collectors.toList()));

        statement.setDate(new Date());

        return statement;
    }

    @Override
    public Statement mapToEntity(final StatementDto dto) {
        Statement statement = modelMapper.map(dto, Statement.class);

        statement.setAbiturient(abiturientRepository.findById(dto.getId()).orElse(null));
        statement.setCertificates(mapCtCertificates(dto.getCertificates()
            .stream()
            .map(CtCertificateDto::getId)
            .collect(Collectors.toList())));
        statement.setEducationDocument(educationDocumentRepository.findById(statement.getEducationDocument().getId())
                .orElse(null));
        statement.setSpecialties(dto.getSpecialities()
                .stream()
                .map(Specialty::valueOf)
                .collect(Collectors.toList()));

        try {
            statement.setDate(dateFormat.parse(dto.getDate()));
        } catch (ParseException e) {
            statement.setDate(null);
        }

        return statement;
    }

    @Override
    public Statement mapToEntity(final Statement entity, final StatementUpdateRequest updateRequest) {
        modelMapper.map(updateRequest, entity);

        if (updateRequest.getCertificateIdsList() != null)
            entity.setCertificates(mapCtCertificates(updateRequest.getCertificateIdsList()));

        if (updateRequest.getEducationDocumentId() != null)
            entity.setEducationDocument(educationDocumentRepository.findById(updateRequest.getEducationDocumentId())
                .orElse(null));

        if (updateRequest.getSpecialities() != null)
            entity.setSpecialties(updateRequest.getSpecialities()
                    .stream()
                    .map(Specialty::valueOf).collect(Collectors.toList()));

        return entity;
    }
}
