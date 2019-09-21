package men.brakh.abiturient.model.ctCertificate.service;

import men.brakh.abiturient.exception.BadRequestException;
import men.brakh.abiturient.mapping.mapper.DtoMapper;
import men.brakh.abiturient.mapping.presenter.EntityPresenter;
import men.brakh.abiturient.model.ctCertificate.CtCertificate;
import men.brakh.abiturient.model.ctCertificate.Subject;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateDto;
import men.brakh.abiturient.model.ctCertificate.dto.CtCertificateUpdateRequest;
import men.brakh.abiturient.model.ctCertificate.repository.CtCertificateRepository;
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

    private CtCertificateRepository ctCertificateRepository;


    public CtCertificateServiceImpl(final CRUDRepository<CtCertificate, Integer> crudRepository,
                                    final DtoMapper<CtCertificateDto, CtCertificate> dtoMapper,
                                    final EntityPresenter<CtCertificate, CtCertificateDto> entityPresenter,
                                    final Validator validator) {

        super(crudRepository, dtoMapper, entityPresenter, validator);

        ctCertificateRepository = (CtCertificateRepository) crudRepository;
    }

    private void throwIfCertificateWithYearAndSubjectAlreadyExist(final Integer year, final String subjectString)
            throws BadRequestException {
        Subject subject = Subject.fromSubjectName(subjectString);
        List<CtCertificate> ctCertificates = ctCertificateRepository.findByYearAndSubject(year, subject);
        if (ctCertificates.size() > 0) {
            throw new BadRequestException("You already have " + subjectString + " certificate for " + year + " year");
        }
    }

    private void throwIfCertificateWithIdentifierAndNumberAlreadyExist(final String identifier, String number) throws BadRequestException {
        List<CtCertificate> ctCertificates = ctCertificateRepository.findByCertificateIdentifiers(identifier, number);
        if (ctCertificates.size() > 0) {
            throw new BadRequestException("You already have ct certificate with identifier "
                    + identifier + " and number " + number);
        }
    }

    @Override
    public CtCertificateDto create(final CtCertificateCreateRequest createRequest) throws BadRequestException {
        throwIfCertificateWithYearAndSubjectAlreadyExist(createRequest.getYear(), createRequest.getSubject());
        throwIfCertificateWithIdentifierAndNumberAlreadyExist(createRequest.getCertificateIdentifier(),
                createRequest.getCertificateNumber());

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

    @Override
    public List<CtCertificateDto> getByAbiturientId(final Integer abiturientId) {
        return getTemplate.findBy(
                () -> ctCertificateRepository.findByAbiturientId(abiturientId),
                CtCertificateDto.class
        );
    }
}
