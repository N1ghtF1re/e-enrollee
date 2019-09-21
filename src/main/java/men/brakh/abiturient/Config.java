package men.brakh.abiturient;

import men.brakh.abiturient.model.abiturient.mapping.AbiturientDtoMapper;
import men.brakh.abiturient.model.abiturient.mapping.AbiturientEntityPresenter;
import men.brakh.abiturient.model.abiturient.repository.AbiturientJsonRepository;
import men.brakh.abiturient.model.abiturient.repository.AbiturientRepository;
import men.brakh.abiturient.model.abiturient.service.AbiturientService;
import men.brakh.abiturient.model.abiturient.service.AbiturientServiceImpl;
import men.brakh.abiturient.model.ctCertificate.mapping.CtCertificateDtoMapper;
import men.brakh.abiturient.model.ctCertificate.mapping.CtCertificateEntityPresenter;
import men.brakh.abiturient.model.ctCertificate.repository.CtCertificateJsonRepository;
import men.brakh.abiturient.model.ctCertificate.repository.CtCertificateRepository;
import men.brakh.abiturient.model.ctCertificate.service.CtCertificateService;
import men.brakh.abiturient.model.ctCertificate.service.CtCertificateServiceImpl;
import men.brakh.abiturient.model.educationDocument.mapping.EducationDocumentDtoMapper;
import men.brakh.abiturient.model.educationDocument.mapping.EducationDocumentEntityPresenter;
import men.brakh.abiturient.model.educationDocument.repository.EducationDocumentJsonRepository;
import men.brakh.abiturient.model.educationDocument.repository.EducationDocumentRepository;
import men.brakh.abiturient.model.educationDocument.service.EducationDocumentService;
import men.brakh.abiturient.model.educationDocument.service.EducationDocumentServiceImpl;
import men.brakh.abiturient.model.statement.mapping.StatementDtoMapper;
import men.brakh.abiturient.model.statement.mapping.StatementEntityPresenter;
import men.brakh.abiturient.model.statement.repository.StatementJsonRepository;
import men.brakh.abiturient.model.statement.repository.StatementRepository;
import men.brakh.abiturient.model.statement.service.StatementService;
import men.brakh.abiturient.model.statement.service.StatementServiceImpl;
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
    private static AbiturientRepository abiturientRepository = new AbiturientJsonRepository();
    private static AbiturientDtoMapper abiturientDtoMapper = new AbiturientDtoMapper(modelMapper, dateFormat);
    private static AbiturientEntityPresenter abiturientEntityPresenter = new AbiturientEntityPresenter(modelMapper, dateFormat);


    /* CT CERTIFICATE */
    private static CtCertificateRepository ctCertificateRepository = new CtCertificateJsonRepository(abiturientRepository);
    private static CtCertificateDtoMapper ctCertificateDtoMapper = new CtCertificateDtoMapper(modelMapper, abiturientRepository);
    private static CtCertificateEntityPresenter ctCertificateEntityPresenter = new CtCertificateEntityPresenter(modelMapper);


    /* EDUCATION DOCUMENT */
    private static EducationDocumentRepository educationDocumentRepository = new EducationDocumentJsonRepository(abiturientRepository);
    private static EducationDocumentDtoMapper educationDocumentDtoMapper = new EducationDocumentDtoMapper(modelMapper, abiturientRepository);
    private static EducationDocumentEntityPresenter educationDocumentEntityPresenter = new EducationDocumentEntityPresenter(modelMapper);

    /* STATEMENT */
    private static StatementRepository statementRepository = new StatementJsonRepository(abiturientRepository, educationDocumentRepository, ctCertificateRepository);
    private static StatementDtoMapper statementDtoMapper = new StatementDtoMapper(modelMapper, dateFormat, abiturientRepository, ctCertificateRepository, educationDocumentRepository);
    private static StatementEntityPresenter statementEntityPresenter = new StatementEntityPresenter(modelMapper, dateFormat, ctCertificateEntityPresenter, educationDocumentEntityPresenter);


    /**
     * Abiturient Service.
     */
    public static AbiturientService abiturientService= new AbiturientServiceImpl(
            abiturientRepository, abiturientDtoMapper, abiturientEntityPresenter, validator,
            Arrays.asList(ctCertificateRepository, educationDocumentRepository, statementRepository)
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
     * Statement Service
     */
    public static StatementService statementService = new StatementServiceImpl(
            statementRepository, statementDtoMapper, statementEntityPresenter, validator
    );

}
