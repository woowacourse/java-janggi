package janggi.domain;

public class Score {
    private final double point;

    public Score(double point) {
        validate(point);
        this.point = point;
    }

    private void validate(double point) {
        if (point < 0) {
            throw new IllegalArgumentException("점수는 음수가 될 수 없습니다.");
        }
    }

    public Score plus(Score other) {
        return new Score(point + other.point);
    }

    public Score multiply(double factor) {
        return new Score(this.point * factor);
    }

    public boolean isGreaterThan(Score other) {
        return point > other.point;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Score score)) return false;

        return Double.compare(point, score.point) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(point);
    }
}
