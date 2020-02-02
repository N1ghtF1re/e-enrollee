package men.brakh.enrollment.application.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=SubjectsInCerficiateEqualsSubjectsForSpecialitiesVaidator.class)
public @interface SubjectsInCerficiateEqualsSubjectsForSpecialities {
    String message() default "Must contain only specialities which need subjects as in passed certificates";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
