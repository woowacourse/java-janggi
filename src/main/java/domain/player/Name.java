package domain.player;

import java.util.regex.Pattern;

public record Name(String name) {

    private static final int NAME_MAX_LENGTH = 10;
    private static final int NAME_MIN_LENGTH = 2;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]+$");

    public Name {
        validate(name);
    }

    private static void validate(String name) {
        validateContainsNumber(name);
        validateLength(name);
    }

    private static void validateContainsNumber(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("[ERROR] 이름은 한글, 영문, 숫자만 가능합니다.");
        }
    }

    private static void validateLength(String name) {
        if (name.length() > NAME_MAX_LENGTH || name.length() < NAME_MIN_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 이름은 2글자에서 10글자 사이여야 합니다.");
        }
    }

}
