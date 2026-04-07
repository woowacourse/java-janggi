package domain.player;

public record Name(
        String name
) {
    private static final int MINIMUM_LENGTH = 2;
    private static final int MAXIMUM_LENGTH = 5;
    private static final String NAME_PATTERN = String.format("^[A-Za-z]{%d,%d}", MINIMUM_LENGTH, MAXIMUM_LENGTH);
    private static final String INVALID_PATTERN = "이름은 " + MINIMUM_LENGTH + "-" + MAXIMUM_LENGTH + " 길이의 영문자여야 합니다.";

    public Name {
        validatePattern(name);
    }

    private void validatePattern(final String name) {
        if (!name.matches(NAME_PATTERN)) {
            throw new IllegalArgumentException(INVALID_PATTERN);
        }
    }
}
