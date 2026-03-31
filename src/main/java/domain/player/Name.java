package domain.player;

public record Name(
        String name
) {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 5;
    private static final String VALID_NAME_REGEX = String.format("^[A-Za-z]{%d,%d}$", MIN_NAME_LENGTH, MAX_NAME_LENGTH);

    public Name {
        validateNotBlank(name);
        validateNameFormat(name);
    }

    private void validateNotBlank(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력하지 않았거나 이름이 공백입니다. 이름은 2~5자의 영문자로 이루어진 문자열만 가능합니다.");
        }
    }

    private void validateNameFormat(final String name) {
        if (!name.matches(VALID_NAME_REGEX)) {
            throw new IllegalArgumentException("이름이 규칙에 맞지 않습니다. 이름은 2~5자의 영문자로 이루어진 문자열만 가능합니다. name: " + name);
        }
    }
}
