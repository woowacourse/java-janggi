package domain.player;

public record Name(String name) {

    private static final int NAME_MAX_LENGTH = 10;
    private static final int NAME_MIN_LENGTH = 2;
    private static final String MATCH_NUMBER_PATTERN = ".*\\d.*";

    public Name {
        validate(name);
    }

    private static void validate(String name) {
        validateContainsNumber(name);
        validateLength(name);
    }

    private static void validateContainsNumber(String name) {
        if (name.matches(MATCH_NUMBER_PATTERN)) {
            throw new IllegalArgumentException("[ERROR] 이름은 한글, 영문만 가능합니다.");
        }
    }

    private static void validateLength(String name) {
        if (name.length() > NAME_MAX_LENGTH || name.length() < NAME_MIN_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 이름은 2글자에서 10글자 사이여야 합니다.");
        }
    }
}