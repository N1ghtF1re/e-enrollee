package men.brakh.enrollment;

import men.brakh.enrollment.model.enrollee.mapping.EnrolleeDtoMapper;
import men.brakh.enrollment.model.enrollee.mapping.EnrolleeEntityPresenter;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeJsonRepository;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.enrollee.service.EnrolleeService;
import men.brakh.enrollment.model.enrollee.service.EnrolleeServiceImpl;
import men.brakh.enrollment.model.ctCertificate.mapping.CtCertificateDtoMapper;
import men.brakh.enrollment.model.ctCertificate.mapping.CtCertificateEntityPresenter;
import men.brakh.enrollment.model.ctCertificate.repository.CtCertificateJsonRepository;
import men.brakh.enrollment.model.ctCertificate.repository.CtCertificateRepository;
import men.brakh.enrollment.model.ctCertificate.service.CtCertificateService;
import men.brakh.enrollment.model.ctCertificate.service.CtCertificateServiceImpl;
import men.brakh.enrollment.model.educationDocument.mapping.EducationDocumentDtoMapper;
import men.brakh.enrollment.model.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.enrollment.model.educationDocument.repository.EducationDocumentJsonRepository;
import men.brakh.enrollment.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.enrollment.model.educationDocument.service.EducationDocumentService;
import men.brakh.enrollment.model.educationDocument.service.EducationDocumentServiceImpl;
import men.brakh.enrollment.model.universityApplication.mapping.UniversityApplicationDtoMapper;
import men.brakh.enrollment.model.universityApplication.mapping.UniversityApplicationEntityPresenter;
import men.brakh.enrollment.model.universityApplication.repository.UniversityApplicationJsonRepository;
import men.brakh.enrollment.model.universityApplication.repository.UniversityApplicationRepository;
import men.brakh.enrollment.model.universityApplication.service.UniversityApplicationService;
import men.brakh.enrollment.model.universityApplication.service.UniversityApplicationServiceImpl;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Crutch Dependency Injection.
 */
public class Config {
    private static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper
             .getConfiguration()
                  .setAmbiguityIgnored(true)
                  .setPropertyCondition(Conditions.isNotNull());
    }


    private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = validatorFactory.usingContext().getValidator();

    /* ABITURIENT */
    private static EnrolleeRepository enrolleeRepository = new EnrolleeJsonRepository();
    private static EnrolleeDtoMapper enrolleeDtoMapper = new EnrolleeDtoMapper(modelMapper, dateFormat);
    private static EnrolleeEntityPresenter enrolleeEntityPresenter = new EnrolleeEntityPresenter(modelMapper, dateFormat);


    /* CT CERTIFICATE */
    private static CtCertificateRepository ctCertificateRepository = new CtCertificateJsonRepository(enrolleeRepository);
    private static CtCertificateDtoMapper ctCertificateDtoMapper = new CtCertificateDtoMapper(modelMapper, enrolleeRepository);
    private static CtCertificateEntityPresenter ctCertificateEntityPresenter = new CtCertificateEntityPresenter(modelMapper);


    /* EDUCATION DOCUMENT */
    private static EducationDocumentRepository educationDocumentRepository = new EducationDocumentJsonRepository(enrolleeRepository);
    private static EducationDocumentDtoMapper educationDocumentDtoMapper = new EducationDocumentDtoMapper(modelMapper, enrolleeRepository);
    private static EducationDocumentEntityPresenter educationDocumentEntityPresenter = new EducationDocumentEntityPresenter(modelMapper);

    /* APPLICATION */
    private static UniversityApplicationRepository universityApplicationRepository = new UniversityApplicationJsonRepository(enrolleeRepository, educationDocumentRepository, ctCertificateRepository);
    private static UniversityApplicationDtoMapper universityApplicationDtoMapper = new UniversityApplicationDtoMapper(modelMapper, dateFormat, enrolleeRepository, ctCertificateRepository, educationDocumentRepository);
    private static UniversityApplicationEntityPresenter universityApplicationEntityPresenter = new UniversityApplicationEntityPresenter(modelMapper, dateFormat, ctCertificateEntityPresenter, educationDocumentEntityPresenter);


    /**
     * Enrollee Service.
     */
    public static EnrolleeService enrolleeService= new EnrolleeServiceImpl(
            enrolleeRepository, enrolleeDtoMapper, enrolleeEntityPresenter, validator,
            Arrays.asList(ctCertificateRepository, educationDocumentRepository, universityApplicationRepository)
    );

    /**
     * Ct Certificate Service
     */
    public static CtCertificateService ctCertificateService = new CtCertificateServiceImpl(
        ctCertificateRepository, ctCertificateDtoMapper, ctCertificateEntityPresenter, validator
    );

    /**
     * Education Document Service
     */
    public static EducationDocumentService educationDocumentService = new EducationDocumentServiceImpl(
        educationDocumentRepository, educationDocumentDtoMapper, educationDocumentEntityPresenter, validator
    );

    /**
     * UniversityApplication Service
     */
    public static UniversityApplicationService universityApplicationService = new UniversityApplicationServiceImpl(
            universityApplicationRepository, universityApplicationDtoMapper, universityApplicationEntityPresenter, validator
    );

}
