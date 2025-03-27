package domain.janggi;

public record Score(
        float value
) {

    private static final float MINIMUM_SCORE = 0;
    private static final float MAXIMUM_SCORE = 73.5f;

    public Score {
        validateRange(value);
        validateDecimalPart(value);
    }

    public Score plus(final Score score) {
        return new Score(value + score.value);
    }

    private void validateRange(final float value) {
        if (value < MINIMUM_SCORE || value > MAXIMUM_SCORE) {
            throw new IllegalArgumentException("잘못된 범위의 점수입니다.");
        }
    }

    private void validateDecimalPart(final float value) {
        final float valueDecimalPart = value - (int) Math.floor(value);
        if (valueDecimalPart == 0.0f || valueDecimalPart == 0.5f) {
            return;
        }
        throw new IllegalArgumentException("점수의 소수점자리는 0.0이나 0.5 이어야합니다.");
    }
}
