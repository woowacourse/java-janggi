package view;

public enum FormationType {
    DEFAULT("1"),
    INNER_HORSE("2"),
    LEFT_INNER_HORSE("3"),
    RIGHT_INNER_HORSE("4");

    private static final String ERROR_INVALID_INPUT = "잘못된 입력 값입니다. 1~4 값을 입력해주세요.";

    private final String inputValue;

    FormationType(String inputValue) {
        this.inputValue = inputValue;
    }

    public static FormationType from(String input) {
        for (FormationType formationType : values()) {
            if (formationType.inputValue.equals(input)) {
                return formationType;
            }
        }
        throw new IllegalArgumentException(ERROR_INVALID_INPUT);
    }
}
