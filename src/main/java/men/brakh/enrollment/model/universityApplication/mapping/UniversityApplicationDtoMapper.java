package men.brakh.enrollment.model.universityApplication.mapping;

import men.brakh.enrollment.mapping.mapper.CreateDtoMapper;
import men.brakh.enrollment.mapping.mapper.DtoMapper;
import men.brakh.enrollment.mapping.mapper.UpdateDtoMapper;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.model.ctCertificate.repository.CtCertificateRepository;
import men.brakh.enrollment.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.specialty.Specialty;
import men.brakh.enrollment.model.universityApplication.UniversityApplication;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationCreateRequest;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationDto;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationUpdateRequest;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UniversityApplicationDtoMapper implements DtoMapper<UniversityApplicationDto, UniversityApplication>,
        CreateDtoMapper<UniversityApplicationCreateRequest, UniversityApplication>, UpdateDtoMapper<UniversityApplicationUpdateRequest, UniversityApplication> {

    private final ModelMapper modelMapper;
    private final EnrolleeRepository enrolleeRepository;
    private final CtCertificateRepository ctCertificateRepository;
    private final EducationDocumentRepository educationDocumentRepository;
    private final DateFormat dateFormat;

    public UniversityApplicationDtoMapper(final ModelMapper modelMapper,
                              final DateFormat dateFormat,
                              final EnrolleeRepository enrolleeRepository,
                              final CtCertificateRepository ctCertificateRepository,
                              final EducationDocumentRepository educationDocumentRepository) {
        this.modelMapper = modelMapper;
        this.dateFormat = dateFormat;
        this.enrolleeRepository = enrolleeRepository;
        this.ctCertificateRepository = ctCertificateRepository;
        this.educationDocumentRepository = educationDocumentRepository;

        modelMapper.typeMap(UniversityApplicationCreateRequest.class, UniversityApplication.class)
                .addMappings(mapping -> mapping.skip(UniversityApplication::setId));
        modelMapper.typeMap(UniversityApplicationUpdateRequest.class, UniversityApplication.class)
                .addMappings(mapping -> mapping.skip(UniversityApplication::setId));
        modelMapper.typeMap(UniversityApplicationDto.class, UniversityApplication.class)
                .addMappings(mapping -> mapping.skip(UniversityApplication::setCertificates))
                .addMappings(mapping -> mapping.skip(UniversityApplication::setDate));

    }

    private List<CtCertificate> mapCtCertificates(List<Integer> ids) {
        return ids == null ? Collections.emptyList() : ids
                .stream()
                .map(id -> ctCertificateRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
    }

    @Override
    public UniversityApplication mapToEntity(final UniversityApplicationCreateRequest createRequest) {
        UniversityApplication universityApplication = modelMapper.map(createRequest, UniversityApplication.class);

        universityApplication.setEnrollee(enrolleeRepository.findById(createRequest.getEnrolleeId()).orElse(null));

        universityApplication.setCertificates(mapCtCertificates(createRequest.getCertificateIdsList()));

        universityApplication.setEducationDocument(educationDocumentRepository.findById(createRequest.getEducationDocumentId())
                .orElse(null));

        universityApplication.setSpecialties(createRequest.getSpecialities() == null ? Collections.emptyList() :
            createRequest.getSpecialities()
                .stream()
                .map(Specialty::valueOf).collect(Collectors.toList()));

        universityApplication.setDate(new Date());

        return universityApplication;
    }

    @Override
    public UniversityApplication mapToEntity(final UniversityApplicationDto dto) {
        UniversityApplication universityApplication = modelMapper.map(dto, UniversityApplication.class);

        universityApplication.setEnrollee(enrolleeRepository.findById(dto.getId()).orElse(null));
        universityApplication.setCertificates(mapCtCertificates(dto.getCertificates()
            .stream()
            .map(CtCertificateDto::getId)
            .collect(Collectors.toList())));
        universityApplication.setEducationDocument(educationDocumentRepository.findById(universityApplication.getEducationDocument().getId())
                .orElse(null));
        universityApplication.setSpecialties(dto.getSpecialities() == null ? Collections.emptyList() :
            dto.getSpecialities()
                .stream()
                .map(Specialty::valueOf)
                .collect(Collectors.toList()));

        try {
            universityApplication.setDate(dateFormat.parse(dto.getDate()));
        } catch (ParseException e) {
            universityApplication.setDate(null);
        }

        return universityApplication;
    }

    @Override
    public UniversityApplication mapToEntity(final UniversityApplication entity, final UniversityApplicationUpdateRequest updateRequest) {
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
