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
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = validatorFactory.usingContext().getValidator();

    /* ABITURIENT */
    private static AbiturientRepository abiturientRepository = new AbiturientJsonRepository();
    private static AbiturientDtoMapper abiturientDtoMapper = new AbiturientDtoMapper(modelMapper);
    private static AbiturientEntityPresenter abiturientEntityPresenter = new AbiturientEntityPresenter(modelMapper);

    /**
     * Abiturient Service.
     */
    public static AbiturientService abiturientService= new AbiturientServiceImpl(
            abiturientRepository, abiturientDtoMapper, abiturientEntityPresenter, validator
    );

    /* CT CERTIFICATE */
    private static CtCertificateRepository ctCertificateRepository = new CtCertificateJsonRepository(abiturientRepository);
    private static CtCertificateDtoMapper ctCertificateDtoMapper = new CtCertificateDtoMapper(modelMapper, abiturientRepository);
    private static CtCertificateEntityPresenter ctCertificateEntityPresenter = new CtCertificateEntityPresenter(modelMapper);

    /**
     * Ct Certificate Service
     */
    public static CtCertificateService ctCertificateService = new CtCertificateServiceImpl(
        ctCertificateRepository, ctCertificateDtoMapper, ctCertificateEntityPresenter, validator
    );

}
