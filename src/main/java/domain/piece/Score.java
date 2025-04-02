package domain.piece;

public record Score(
        double score
) {
    public static final Score ZERO = new Score(0);

    public Score {
        validatePositive(score);
    }

    private void validatePositive(double score) {
        if (score < 0) {
            throw new IllegalArgumentException("점수는 음수가 될 수 없습니다");
        }
    }

    public Score plus(Score score) {
        return new Score(this.score + score.score);
    }
}
