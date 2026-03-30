package dto;

import exception.JanggiInputException;
import java.util.StringTokenizer;

public record SelectPositionRequest(int row, int col) {
    public static SelectPositionRequest of(String rawInput) {
        requireNonBlank(rawInput);
        return correctFormatPosition(rawInput);
    }

    private static SelectPositionRequest correctFormatPosition(String rawInput) {
        StringTokenizer tokenizer = new StringTokenizer(rawInput, ",");
        try {
            return new SelectPositionRequest(
                    Integer.parseInt(tokenizer.nextToken()),
                    Integer.parseInt(tokenizer.nextToken())
            );
        } catch (NumberFormatException e) {
            throw new JanggiInputException("[ERROR] 올바른 형식이 아닙니다.");
        }
    }

    private static void requireNonBlank(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            throw new JanggiInputException("[ERROR] 올바른 형식이 아닙니다.");
        }
    }
}
