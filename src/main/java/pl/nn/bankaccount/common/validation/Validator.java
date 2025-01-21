package pl.nn.bankaccount.common.validation;

import static lombok.AccessLevel.PRIVATE;

import java.util.Objects;
import lombok.NoArgsConstructor;
import pl.nn.bankaccount.common.validation.dto.ValidationException;

@NoArgsConstructor(access = PRIVATE)
public class Validator {

    public static <T> void checkNotNull(T value, String message) {
        if (Objects.isNull(value)) {
            throw new ValidationException(message);
        }
    }

    public static void checkNotBlank(String value, String message) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new ValidationException(message);
        }
    }

    public static void checkArgument(boolean condition, String message) {
        if (!condition) {
            throw new ValidationException(message);
        }
    }
}
