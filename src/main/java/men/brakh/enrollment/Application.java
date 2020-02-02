package men.brakh.enrollment;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Validator validator() {
    final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    return validatorFactory.usingContext().getValidator();
  }
}