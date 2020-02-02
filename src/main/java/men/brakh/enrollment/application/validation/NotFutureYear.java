package men.brakh.enrollment.application.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=NotFutureYearValidator.class)
public @interface NotFutureYear {
    String message() default "You can't use future year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
