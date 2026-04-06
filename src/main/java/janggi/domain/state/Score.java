package janggi.domain.state;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import java.util.Objects;

public class Score implements Comparable<Score> {

    private double value;

    public Score(Side side) {
        value = side.getDefaultScore();
    }

    public void add(Piece piece) {
        this.value += piece.getScore();
    }

    public void subtract(Piece piece) {
        this.value -= piece.getScore();
    }

    public boolean isBelow(double compare) {
        return value <= compare;
    }

    @Override
    public int compareTo(Score other) {
        return Double.compare(this.value, other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Score score = (Score) o;
        return Double.compare(score.value, this.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
