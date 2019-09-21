package men.brakh.abiturient.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=SingleAbiturientValidator.class)
public @interface SingleAbutirient {
    String message() default "One abiturient must own all subentities";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
