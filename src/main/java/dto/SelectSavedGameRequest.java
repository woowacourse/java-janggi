package dto;

import exception.JanggiInputException;

public record SelectSavedGameRequest(long gameId) {
    public static SelectSavedGameRequest of(String rawInput) {
        requireNonBlank(rawInput);

        try {
            return new SelectSavedGameRequest(Long.parseLong(rawInput));
        } catch (NumberFormatException e) {
            throw new JanggiInputException("[ERROR] 숫자가 아닌 입력 값을 입력하셨습니다." );
        }
    }

    private static void requireNonBlank(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            throw new JanggiInputException("[ERROR] 빈 값을 입력하셨습니다.");
        }
    }
}
