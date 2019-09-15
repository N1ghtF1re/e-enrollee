package men.brakh.abiturient.model.ctCertificate.service;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateUpdateRequest;
import men.brakh.abiturient.repository.CRUDRepository;
import men.brakh.abiturient.service.AbstractCRUDEntityService;

import javax.validation.Validator;
import java.util.List;

public class CtCertificateServiceImpl extends AbstractCRUDEntityService<
        CtCertificate,
        CtCertificateDto,
        CtCertificateCreateRequest,
        CtCertificateUpdateRequest,
        Integer
        > implements CtCertificateService {


    public CtCertificateServiceImpl(final CRUDRepository<CtCertificate, Integer> crudRepository,
                                    final DtoMapper<CtCertificateDto, CtCertificate> dtoMapper,
                                    final EntityPresenter<CtCertificate, CtCertificateDto> entityPresenter,
                                    final Validator validator) {

        super(crudRepository, dtoMapper, entityPresenter, validator);
    }

    @Override
    public CtCertificateDto create(final CtCertificateCreateRequest createRequest) throws BadRequestException {
        return createTemplate.save(createRequest, CtCertificateDto.class);
    }

    @Override
    public void delete(final Integer id) throws BadRequestException {
        deleteTemplate.delete(id);
    }

    @Override
    public CtCertificateDto getById(final Integer id) throws BadRequestException {
        return getTemplate.getById(id, CtCertificateDto.class);
    }

    @Override
    public List<CtCertificateDto> getAll() {
        return getTemplate.getAll(CtCertificateDto.class);
    }

    @Override
    public CtCertificateDto update(final Integer id, final CtCertificateUpdateRequest updateRequest) throws BadRequestException {
        return updateTemplate.update(id, updateRequest, CtCertificateDto.class);
    }

}
