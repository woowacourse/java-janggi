package domain.player;

public record Name(
        String name
) {

    private static final int MINIMUM_LENGTH = 2;
    private static final int MAXIMUM_LENGTH = 5;
    private static final String NAME_PATTERN = String.format("^[A-Za-z]{%d,%d}", MINIMUM_LENGTH, MAXIMUM_LENGTH);

    public Name {
        validateEmpty(name);
        validatePattern(name);
    }

    private void validateEmpty(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("빈 칸 입력 불가능");
        }
    }

    private void validatePattern(final String name) {
        if (!name.matches(NAME_PATTERN)) {
            throw new IllegalArgumentException("이름 규칙에 맞지 않음");
        }
    }
}
