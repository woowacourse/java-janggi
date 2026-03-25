package domain;

public class Score {
    private final int value;

    public Score(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("점수는 0 이상 이어야합니다.");
        }
    }
}
