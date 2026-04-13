package domain.player;


import common.JanggiException;

public record Name(String value) {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 8;
    private static final String INVALID_NAME_MESSAGE =
            "닉네임은 %d~%d글자여야 합니다.".formatted(MIN_NAME_LENGTH, MAX_NAME_LENGTH);

    public Name(String value) {
        String trimmed = value.trim();
        validateName(trimmed);
        this.value = trimmed;
    }

    private void validateName(String value) {
        if (isInvalidLength(value) || hasInvalidCharacter(value)) {
            throw new JanggiException(INVALID_NAME_MESSAGE);
        }
    }

    private boolean isInvalidLength(String value) {
        return value.length() < MIN_NAME_LENGTH || value.length() > MAX_NAME_LENGTH;
    }

    private boolean hasInvalidCharacter(String value) {
        return value.chars()
                .filter(this::isInvalidCharacter)
                .findAny()
                .isPresent();
    }

    private boolean isInvalidCharacter(int character) {
        return !isEnglish(character) && !isNumber(character) && !isKorean(character);
    }

    private boolean isEnglish(int character) {
        return character >= 'a' && character <= 'z' || character >= 'A' && character <= 'Z';
    }

    private boolean isNumber(int character) {
        return character >= '0' && character <= '9';
    }

    private boolean isKorean(int character) {
        return character >= '가' && character <= '힣';
    }
}
