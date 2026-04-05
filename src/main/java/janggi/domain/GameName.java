package janggi.domain;

public record GameName(String name) {
    private static final int MIN_NAME_SIZE = 2;
    private static final int MAX_NAME_SIZE = 20;

    private static final String INVALID_NAME_SIZE_MESSAGE = "이름은 2글자 이상, 20글자 이하로만 가능합니다.";

    public GameName(String name) {
        this.name = validate(name);
    }

    private static String validate(String name) {
        if(name.length() < MIN_NAME_SIZE || name.length() > MAX_NAME_SIZE) {
            throw new IllegalArgumentException(INVALID_NAME_SIZE_MESSAGE);
        }
        return name;
    }
}
