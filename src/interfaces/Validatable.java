package interfaces;

public interface Validatable {
    void validate();

    default boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    static void requirePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }
}
