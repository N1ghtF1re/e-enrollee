package men.brakh.enrollment.domain.universityApplication.service;

import java.util.List;
import javax.validation.Validator;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.domain.universityApplication.EducationType;
import men.brakh.enrollment.domain.universityApplication.UniversityApplication;
import men.brakh.enrollment.domain.universityApplication.dto.UniversityApplicationCreateRequest;
import men.brakh.enrollment.domain.universityApplication.dto.UniversityApplicationDto;
import men.brakh.enrollment.domain.universityApplication.dto.UniversityApplicationUpdateRequest;
import men.brakh.enrollment.domain.universityApplication.mapping.UniversityApplicationDtoMapper;
import men.brakh.enrollment.domain.universityApplication.mapping.UniversityApplicationEntityPresenter;
import men.brakh.enrollment.domain.universityApplication.repository.UniversityApplicationRepository;
import men.brakh.enrollment.application.service.AbstractCRUDEntityService;
import org.springframework.stereotype.Service;

@Service
public class UniversityApplicationServiceImpl extends AbstractCRUDEntityService<
        UniversityApplication,
        UniversityApplicationDto,
        UniversityApplicationCreateRequest,
        UniversityApplicationUpdateRequest,
        Integer
        > implements UniversityApplicationService {

    private final UniversityApplicationRepository universityApplicationRepository;

    public UniversityApplicationServiceImpl(final UniversityApplicationRepository crudRepository,
                                final UniversityApplicationDtoMapper dtoMapper,
                                final UniversityApplicationEntityPresenter entityPresenter,
                                final Validator validator) {
        super(crudRepository, dtoMapper, entityPresenter, validator);
        universityApplicationRepository = crudRepository;
    }

    private void thowIfEnrolleeAlreadyHasUniversityApplication(final Integer enrolleeType,
                                                               final String type) throws BadRequestException {
        EducationType educationType = EducationType.valueOf(type);
        List<UniversityApplication> applications =
                universityApplicationRepository.findByEnrolleeIdAndType(enrolleeType, educationType);

        if (applications.size() > 0) {
            throw new BadRequestException("Enrollee can't have more than one university application of the same type. "
                    + "Remove old university application to add new, or just create application of the different type");
        }


    }

    @Override
    public UniversityApplicationDto create(final UniversityApplicationCreateRequest createRequest) throws BadRequestException {
        thowIfEnrolleeAlreadyHasUniversityApplication(createRequest.getEnrolleeId(), createRequest.getType());

        return createTemplate.save(createRequest, UniversityApplicationDto.class);
    }

    @Override
    public void delete(final Integer id) throws BadRequestException {
        deleteTemplate.delete(id);
    }

    @Override
    public UniversityApplicationDto getById(final Integer id) throws BadRequestException {
        return getTemplate.getById(id, UniversityApplicationDto.class);
    }

    @Override
    public List<UniversityApplicationDto> getAll() {
        return getTemplate.getAll(UniversityApplicationDto.class);
    }

    @Override
    public UniversityApplicationDto update(final Integer id,
                               final UniversityApplicationUpdateRequest updateRequest) throws BadRequestException {
        return updateTemplate.update(id, updateRequest, UniversityApplicationDto.class);
    }
}
