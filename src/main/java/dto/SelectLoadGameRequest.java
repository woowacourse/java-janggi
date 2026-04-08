package dto;

import exception.JanggiInputException;

public record SelectLoadGameRequest(boolean select) {
    private static final String POSITIVE = "y";
    private static final String NEGATIVE = "n";

    public static SelectLoadGameRequest of(String rawInput) {
        requireNonBlank(rawInput);
        requireCorrectFormat(rawInput);
        return parse(rawInput);
    }

    private static void requireNonBlank(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            throw new JanggiInputException("[ERROR] 빈 값을 입력하셨습니다.");
        }
    }

    private static void requireCorrectFormat(String rawInput) {
        if (!rawInput.equals(POSITIVE) && !rawInput.equals(NEGATIVE)) {
            throw new JanggiInputException("[ERROR] 올바른 형식이 아닙니다. y 또는 n 을 입력해주세요.");
        }
    }

    private static SelectLoadGameRequest parse(String rawInput) {
        return new SelectLoadGameRequest(rawInput.equals(POSITIVE));
    }
}
