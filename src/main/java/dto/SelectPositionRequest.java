package dto;

import exception.JanggiInputException;

public record SelectPositionRequest(int row, int col) {

    public static SelectPositionRequest of(String rawInput) {
        requireNonBlank(rawInput);
        requireCorrectFormat(rawInput);
        return parsePosition(rawInput);
    }

    private static void requireNonBlank(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            throw new JanggiInputException("[ERROR] 올바른 형식이 아닙니다.");
        }
    }

    private static void requireCorrectFormat(String rawInput) {
        if (rawInput == null || !rawInput.matches("^\\d+,\\d+$")) {
            throw new JanggiInputException("[ERROR] 올바른 형식이 아닙니다. (예: 1,2)");
        }
    }

    private static SelectPositionRequest parsePosition(String rawInput) {
        String[] parts = rawInput.split(",");
        return new SelectPositionRequest(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        );
    }
}
