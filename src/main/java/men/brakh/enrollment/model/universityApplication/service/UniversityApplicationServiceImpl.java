package men.brakh.enrollment.model.universityApplication.service;

import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.mapping.mapper.DtoMapper;
import men.brakh.enrollment.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.model.universityApplication.UniversityApplication;
import men.brakh.enrollment.model.universityApplication.UniversityApplicationType;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationCreateRequest;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationDto;
import men.brakh.enrollment.model.universityApplication.dto.UniversityApplicationUpdateRequest;
import men.brakh.enrollment.model.universityApplication.repository.UniversityApplicationRepository;
import men.brakh.enrollment.repository.CRUDRepository;
import men.brakh.enrollment.service.AbstractCRUDEntityService;

import javax.validation.Validator;
import java.util.List;

public class UniversityApplicationServiceImpl extends AbstractCRUDEntityService<
        UniversityApplication,
        UniversityApplicationDto,
        UniversityApplicationCreateRequest,
        UniversityApplicationUpdateRequest,
        Integer
        > implements UniversityApplicationService {

    private final UniversityApplicationRepository universityApplicationRepository;

    public UniversityApplicationServiceImpl(final CRUDRepository<UniversityApplication, Integer> crudRepository,
                                final DtoMapper<UniversityApplicationDto, UniversityApplication> dtoMapper,
                                final EntityPresenter<UniversityApplication, UniversityApplicationDto> entityPresenter,
                                final Validator validator) {
        super(crudRepository, dtoMapper, entityPresenter, validator);
        universityApplicationRepository = (UniversityApplicationRepository) crudRepository;
    }

    private void thowIfEnrolleeAlreadyHasUniversityApplication(final Integer enrolleeType, String type) throws BadRequestException {
        UniversityApplicationType universityApplicationType = UniversityApplicationType.valueOf(type);
        List<UniversityApplication> applications =
                universityApplicationRepository.findByEnrolleeIdAndType(enrolleeType, universityApplicationType);

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
