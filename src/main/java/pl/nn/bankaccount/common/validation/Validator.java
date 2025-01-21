package pl.nn.bankaccount.common.validation;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class Validator {

    public static <T> void checkNotNull(T value, String message) {
        if (value == null) {
            throw new ValidationException(message);
        }
    }

    public static void checkNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(message);
        }
    }

    public static void checkArgument(boolean condition, String message) {
        if (!condition) {
            throw new ValidationException(message);
        }
    }
}
