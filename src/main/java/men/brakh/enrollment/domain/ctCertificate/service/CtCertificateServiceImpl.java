package men.brakh.enrollment.domain.ctCertificate.service;

import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Validator;
import men.brakh.enrollment.domain.ctCertificate.CtCertificate;
import men.brakh.enrollment.domain.ctCertificate.Subject;
import men.brakh.enrollment.domain.ctCertificate.dto.CtCertificateCreateRequest;
import men.brakh.enrollment.domain.ctCertificate.dto.CtCertificateDto;
import men.brakh.enrollment.domain.ctCertificate.dto.CtCertificateUpdateRequest;
import men.brakh.enrollment.domain.ctCertificate.repository.CtCertificateRepository;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.application.mapping.mapper.DtoMapper;
import men.brakh.enrollment.application.mapping.presenter.EntityPresenter;
import men.brakh.enrollment.application.service.AbstractCRUDEntityService;
import men.brakh.enrollment.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api/v1/ct-certificates")
@RequestMapping(path = "/api/v1/ct-certificates")
@Transactional
public class CtCertificateServiceImpl extends AbstractCRUDEntityService<
        CtCertificate,
        CtCertificateDto,
        CtCertificateCreateRequest,
        CtCertificateUpdateRequest,
        Integer
        > implements CtCertificateService {

    private CtCertificateRepository ctCertificateRepository;


    public CtCertificateServiceImpl(final CtCertificateRepository crudRepository,
                                    final DtoMapper<CtCertificateDto, CtCertificate> dtoMapper,
                                    final EntityPresenter<CtCertificate, CtCertificateDto> entityPresenter,
                                    final Validator validator) {

        super(crudRepository, dtoMapper, entityPresenter, validator);

        ctCertificateRepository = crudRepository;
    }


    @Override
    @PostMapping
    public @ResponseBody CtCertificateDto create(
        @RequestBody final CtCertificateCreateRequest createRequest
    ) throws BadRequestException {
        throwIfCertificateWithYearAndSubjectAlreadyExist(createRequest.getEnrolleeId(),
            createRequest.getYear(), createRequest.getSubject());
        throwIfCertificateWithIdentifierAndNumberAlreadyExist(createRequest.getCertificateIdentifier(),
                createRequest.getCertificateNumber());

        return createTemplate.save(createRequest, CtCertificateDto.class);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Integer id) throws BadRequestException, ResourceNotFoundException {
        deleteTemplate.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    public @ResponseBody CtCertificateDto getById(@PathVariable("id") final Integer id) throws ResourceNotFoundException {
        return getTemplate.getById(id, CtCertificateDto.class);
    }

    @Override
    @GetMapping
    public @ResponseBody List<CtCertificateDto> getAll() {
        return getTemplate.getAll(CtCertificateDto.class);
    }



    @Override
    @PutMapping("/{id}")
    public @ResponseBody CtCertificateDto update(@PathVariable("id") final Integer id,
                                   @RequestBody final CtCertificateUpdateRequest updateRequest) throws BadRequestException, ResourceNotFoundException {

        final CtCertificate ctCertificate = ctCertificateRepository.findById(id).orElseThrow(BadRequestException::new);

        throwIfCertificateWithYearAndSubjectAlreadyExist(ctCertificate.getEnrollee().getId(),
            updateRequest.getYear(), updateRequest.getSubject());
        throwIfCertificateWithIdentifierAndNumberAlreadyExist(updateRequest.getCertificateIdentifier(),
            updateRequest.getCertificateNumber());

        return updateTemplate.update(id, updateRequest, CtCertificateDto.class);
    }

    @Override
    public List<CtCertificateDto> getByEnrolleeId(final Integer enrolleeId) {
        return getTemplate.findBy(
                () -> ctCertificateRepository.findByEnrolleeId(enrolleeId),
                CtCertificateDto.class
        );
    }



    private void throwIfCertificateWithYearAndSubjectAlreadyExist(final Integer enrolleeId, final Integer year, final String subjectString)
        throws BadRequestException {
        Subject subject = subjectString != null ? Subject.valueOf(subjectString) : null;
        List<CtCertificate> ctCertificates = ctCertificateRepository
            .findAllByEnrolleeIdAndYearAndSubject(enrolleeId, year, subject);
        if (ctCertificates.size() > 0) {
            throw new BadRequestException("You already have " + subjectString + " certificate for " + year + " year");
        }
    }

    private void throwIfCertificateWithIdentifierAndNumberAlreadyExist(final String identifier, String number) throws BadRequestException {
        List<CtCertificate> ctCertificates = ctCertificateRepository
            .findAllByCertificateIdentifierAndCertificateNumber(identifier, number);
        if (ctCertificates.size() > 0) {
            throw new BadRequestException("You already have ct certificate with identifier "
                + identifier + " and number " + number);
        }
    }
}
