package domain.piece;

public record Score(float value) {

    private static final float MIN_VALUE = 0f;
    private static final float MAX_VALUE = 73.5f;

    public Score(
        final float value
    ) {
        this.value = value;
        validateScore(value);

    }

    private void validateScore(final float value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("점수는 0.0 ~ 73.5 사이의 값이어야 합니다.");
        }
    }

    public Score add(final Score score) {
        return new Score(this.value + score.value);
    }

    public float getValue() {
        return value;
    }
}
