package domain.player;

public record Name(String name) {

    public Name {
        validate(name);
    }

    private static void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 값이 될 수 없습니다.");
        }

        if (name.length() < 2 || name.length() > 5) {
            throw new IllegalArgumentException("이름은 2~5글자 사이여야 합니다.");
        }
    }
}
