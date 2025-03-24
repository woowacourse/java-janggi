package domain.score;

public record Score(int value) {
    public Score {
        if (value < 0) {
            throw new IllegalArgumentException("점수는 음수가 될 수 없습니다.");
        }
    }

    public static Score zero() {
        return new Score(0);
    }

    public Score add(Score other) {
        return new Score(value + other.value);
    }
}
