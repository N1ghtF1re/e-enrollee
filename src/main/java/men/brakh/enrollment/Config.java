package men.brakh.enrollment;

import men.brakh.enrollment.infrastructure.authorization.AuthorizationManager;
import men.brakh.enrollment.infrastructure.jdbc.ConnectionPool;
import men.brakh.enrollment.infrastructure.jdbc.HikariConnectionPool;
import men.brakh.enrollment.model.ctCertificate.CtCertificate;
import men.brakh.enrollment.model.ctCertificate.mapping.CtCertificateDtoMapper;
import men.brakh.enrollment.model.ctCertificate.mapping.CtCertificateEntityPresenter;
import men.brakh.enrollment.model.ctCertificate.repository.CtCertificateMysqlRepository;
import men.brakh.enrollment.model.ctCertificate.repository.CtCertificateRepository;
import men.brakh.enrollment.model.ctCertificate.service.CtCertificateService;
import men.brakh.enrollment.model.ctCertificate.service.CtCertificateServiceImpl;
import men.brakh.enrollment.model.educationDocument.EducationDocument;
import men.brakh.enrollment.model.educationDocument.mapping.EducationDocumentDtoMapper;
import men.brakh.enrollment.model.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.enrollment.model.educationDocument.repository.EducationDocumentMysqlRepository;
import men.brakh.enrollment.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.enrollment.model.educationDocument.service.EducationDocumentService;
import men.brakh.enrollment.model.educationDocument.service.EducationDocumentServiceImpl;
import men.brakh.enrollment.model.employee.credentials.EmployeeCredentialsMysqlRepository;
import men.brakh.enrollment.model.employee.credentials.EmployeeCredentialsRepository;
import men.brakh.enrollment.model.employee.mapping.EmployeeDtoMapper;
import men.brakh.enrollment.model.employee.mapping.EmployeeDtoPresenter;
import men.brakh.enrollment.model.employee.repository.EmployeeMysqlRepository;
import men.brakh.enrollment.model.employee.repository.EmployeeRepository;
import men.brakh.enrollment.model.employee.service.EmployeeService;
import men.brakh.enrollment.model.employee.service.EmployeeServiceImpl;
import men.brakh.enrollment.model.enrollee.Enrollee;
import men.brakh.enrollment.model.enrollee.mapping.EnrolleeDtoMapper;
import men.brakh.enrollment.model.enrollee.mapping.EnrolleeEntityPresenter;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeMysqlRepository;
import men.brakh.enrollment.model.enrollee.repository.EnrolleeRepository;
import men.brakh.enrollment.model.enrollee.service.EnrolleeService;
import men.brakh.enrollment.model.enrollee.service.EnrolleeServiceImpl;
import men.brakh.enrollment.model.interimLists.service.InterimListsService;
import men.brakh.enrollment.model.interimLists.service.InterimListsServiceImpl;
import men.brakh.enrollment.model.universityApplication.mapping.UniversityApplicationDtoMapper;
import men.brakh.enrollment.model.universityApplication.mapping.UniversityApplicationEntityPresenter;
import men.brakh.enrollment.model.universityApplication.repository.UniversityApplicationMysqlRepository;
import men.brakh.enrollment.model.universityApplication.repository.UniversityApplicationRepository;
import men.brakh.enrollment.model.universityApplication.service.UniversityApplicationService;
import men.brakh.enrollment.model.universityApplication.service.UniversityApplicationServiceImpl;
import men.brakh.enrollment.repository.impl.MysqlCRUDRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Crutch Dependency Injection.
 */
@SuppressWarnings("unchecked")
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

    private static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    private static ConnectionPool connectionPool = new HikariConnectionPool();

    /* EMPLOYEE */
    private static EmployeeRepository employeeRepository = new EmployeeMysqlRepository(connectionPool, "employee");
    private static EmployeeDtoMapper employeeDtoMapper = new EmployeeDtoMapper(modelMapper);
    private static EmployeeDtoPresenter employeeDtoPresenter = new EmployeeDtoPresenter(modelMapper);
    private static EmployeeCredentialsRepository employeeCredentialsRepository = new EmployeeCredentialsMysqlRepository(
        connectionPool,
        "employee_credentials"
    );

    /* ENROLLEE */
    private static EnrolleeRepository enrolleeRepository = new EnrolleeMysqlRepository(connectionPool, "enrollee");
    private static EnrolleeDtoMapper enrolleeDtoMapper = new EnrolleeDtoMapper(modelMapper, dateFormat);
    private static EnrolleeEntityPresenter enrolleeEntityPresenter = new EnrolleeEntityPresenter(modelMapper, dateFormat);


    /* CT CERTIFICATE */
    private static CtCertificateRepository ctCertificateRepository = new CtCertificateMysqlRepository(connectionPool,
        "ct_certificate", (MysqlCRUDRepository<Enrollee, ?>) enrolleeRepository);
    private static CtCertificateDtoMapper ctCertificateDtoMapper = new CtCertificateDtoMapper(modelMapper, enrolleeRepository);
    private static CtCertificateEntityPresenter ctCertificateEntityPresenter = new CtCertificateEntityPresenter(modelMapper);


    /* EDUCATION DOCUMENT */
    private static EducationDocumentRepository educationDocumentRepository = new EducationDocumentMysqlRepository(
        connectionPool,
        "education_document",
        (MysqlCRUDRepository<Enrollee, ?>) enrolleeRepository);

    private static EducationDocumentDtoMapper educationDocumentDtoMapper = new EducationDocumentDtoMapper(modelMapper, enrolleeRepository);
    private static EducationDocumentEntityPresenter educationDocumentEntityPresenter = new EducationDocumentEntityPresenter(modelMapper);

    /* APPLICATION */
    private static UniversityApplicationRepository universityApplicationRepository = new UniversityApplicationMysqlRepository(
        connectionPool,
        "university_application",
        (MysqlCRUDRepository<Enrollee, ?>) enrolleeRepository,
        (MysqlCRUDRepository<EducationDocument, ?>) educationDocumentRepository,
        (MysqlCRUDRepository<CtCertificate, Integer>) ctCertificateRepository);

    private static UniversityApplicationDtoMapper universityApplicationDtoMapper = new UniversityApplicationDtoMapper(modelMapper, dateFormat, enrolleeRepository, ctCertificateRepository, educationDocumentRepository);
    private static UniversityApplicationEntityPresenter universityApplicationEntityPresenter = new UniversityApplicationEntityPresenter(modelMapper, dateFormat, ctCertificateEntityPresenter, educationDocumentEntityPresenter);


    /**
     * Enrollee Service.
     */
    public static EnrolleeService enrolleeService= new EnrolleeServiceImpl(
            enrolleeRepository, enrolleeDtoMapper, enrolleeEntityPresenter, validator
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

    /**
     * Interim service
     */
    public static InterimListsService interimListsService = new InterimListsServiceImpl(enrolleeEntityPresenter,
            universityApplicationRepository);

    /**
     * Employee Service
     */
    public static EmployeeService employeeService = new EmployeeServiceImpl(
        employeeRepository,
        employeeDtoMapper,
        employeeDtoPresenter,
        validator,
        employeeCredentialsRepository,
        messageDigest
    );

    public static AuthorizationManager authorizationManager = new AuthorizationManager();
}
