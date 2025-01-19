package pl.nn.bankaccount.common;

public class Validator {

    public static <T> void checkNotNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }

    }

    public static void checkNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
