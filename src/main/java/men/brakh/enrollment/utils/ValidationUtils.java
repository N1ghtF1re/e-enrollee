package men.brakh.enrollment.utils;

import men.brakh.enrollment.exception.BadRequestException;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationUtils {
    public static <T> void validateAndThowIfInvalid(Validator validator, T entity) throws BadRequestException {
        if (validator != null) {
            List<String> errors = validator.validate(entity)
                    .stream()
                    .map(violation -> "\t Field: " + violation.getPropertyPath() +
                            ", Invalid Value" + ", Message: " + violation.getMessage())
                    .collect(Collectors.toList());

            if (errors.size() > 0) {
                throw new BadRequestException("\n" + String.join("\n", errors));
            }
        }
    }
}
