package domain;

public record Score(
        float value
) {

    private static final int MINIMUM_SCORE = 0;
    private static final double MAXIMUM_SCORE = 73.5;

    public Score {
        validateRange(value);
        validateDecimalPart(value);
    }

    private void validateRange(final float value) {
        if (value < MINIMUM_SCORE || value > MAXIMUM_SCORE) {
            throw new IllegalArgumentException("잘못된 범위의 점수입니다.");
        }
    }

    private void validateDecimalPart(final float value) {
        final float valueDecimalPart = value - (int) Math.floor(value);
        if (valueDecimalPart == 0.0 || valueDecimalPart == 0.5) {
            return;
        }
        throw new IllegalArgumentException("점수의 소수점자리는 0.0이나 0.5 이어야합니다.");
    }
}
