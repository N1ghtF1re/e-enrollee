package men.brakh.enrollment.domain.ctCertificate.service;

import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Validator;
import men.brakh.enrollment.application.search.SearchRequest;
import men.brakh.enrollment.application.search.SearchResponse;
import men.brakh.enrollment.application.template.SearchTemplate;
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
    private SearchTemplate<CtCertificate, CtCertificateDto> searchTemplate;


    public CtCertificateServiceImpl(final CtCertificateRepository crudRepository,
                                    final DtoMapper<CtCertificateDto, CtCertificate> dtoMapper,
                                    final EntityPresenter<CtCertificate, CtCertificateDto> entityPresenter,
                                    final Validator validator) {

        super(crudRepository, dtoMapper, entityPresenter, validator);

        ctCertificateRepository = crudRepository;
        this.searchTemplate = new SearchTemplate<>(
            crudRepository,
            entityPresenter
        );
    }


    @Override
    @PostMapping
    public @ResponseBody CtCertificateDto create(
        @RequestBody final CtCertificateCreateRequest createRequest
    ) throws BadRequestException {
        throwIfCertificateWithYearAndSubjectAlreadyExist(createRequest.getEnrolleeId(),
            createRequest.getYear(), createRequest.getSubject(), null);
        throwIfCertificateWithIdentifierAndNumberAlreadyExist(createRequest.getCertificateIdentifier(),
                createRequest.getCertificateNumber(), null);

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
    public @ResponseBody CtCertificateDto update(
        @PathVariable("id") final Integer id,
        @RequestBody final CtCertificateUpdateRequest updateRequest
    ) throws BadRequestException, ResourceNotFoundException {

        final CtCertificate ctCertificate = ctCertificateRepository.findById(id).orElseThrow(BadRequestException::new);

        throwIfCertificateWithYearAndSubjectAlreadyExist(ctCertificate.getEnrollee().getId(),
            updateRequest.getYear(), updateRequest.getSubject(), id);
        throwIfCertificateWithIdentifierAndNumberAlreadyExist(updateRequest.getCertificateIdentifier(),
            updateRequest.getCertificateNumber(), id);

        return updateTemplate.update(id, updateRequest, CtCertificateDto.class);
    }

    @Override
    public List<CtCertificateDto> getByEnrolleeId(final Integer enrolleeId) {
        return getTemplate.findBy(
                () -> ctCertificateRepository.findByEnrolleeId(enrolleeId),
                CtCertificateDto.class
        );
    }

    @Override
    @PostMapping(path = "search")
    public @ResponseBody SearchResponse<CtCertificateDto> search(
        @RequestBody final SearchRequest searchRequest
    ) throws BadRequestException {
        return searchTemplate.search(searchRequest, CtCertificateDto.class);
    }


    private void throwIfCertificateWithYearAndSubjectAlreadyExist(
        final Integer enrolleeId,
        final Integer year,
        final String subjectString,
        final Integer certificateId
    )
        throws BadRequestException {
        Subject subject = subjectString != null ? Subject.valueOf(subjectString) : null;
        List<CtCertificate> ctCertificates = ctCertificateRepository
            .findAllByEnrolleeIdAndYearAndSubject(enrolleeId, year, subject);
        if (ctCertificates.size() > 0) {
            final boolean certificateHasAnotherId = certificateId != null
                && ctCertificates.stream()
                .anyMatch(ctCertificate -> !ctCertificate.getId().equals(certificateId));

            if (certificateHasAnotherId) {
                throw new BadRequestException("You already have " + subjectString + " certificate for " + year + " year");
            }
        }
    }

    private void throwIfCertificateWithIdentifierAndNumberAlreadyExist(
        final String identifier,
        final String number,
        final Integer certificateId
    ) throws BadRequestException {
        List<CtCertificate> ctCertificates = ctCertificateRepository
            .findAllByCertificateIdentifierAndCertificateNumber(identifier, number);
        if (ctCertificates.size() > 0) {
            final boolean certificateHasAnotherId = certificateId != null
                && ctCertificates.stream()
                .anyMatch(ctCertificate -> !ctCertificate.getId().equals(certificateId));

            if (certificateHasAnotherId) {
                throw new BadRequestException("You already have ct certificate with identifier "
                    + identifier + " and number " + number);
            }
        }
    }
}
