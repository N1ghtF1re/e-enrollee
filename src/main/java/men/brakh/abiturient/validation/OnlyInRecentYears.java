package men.brakh.abiturient.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=OnlyInRecentYearsValidator.class)
public @interface OnlyInRecentYears {
    String message() default "You can only use certificates which were passed in recent years";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int years() default 1;
}
