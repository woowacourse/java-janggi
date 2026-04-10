package janggi.domain.piece;

import java.util.Objects;

public class Score {

    private final int value;

    public Score(int score) {
        this.value = score;
    }

    public Score add(Score other) {
        return new Score(value + other.value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return value == score.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
