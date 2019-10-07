package men.brakh.enrollment.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=SingleEnrolleeValidator.class)
public @interface SingleEnrollee {
    String message() default "One enrollee must own all subentities";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
