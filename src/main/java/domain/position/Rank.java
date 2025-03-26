package domain.position;

import domain.MovingPattern;
import janggiexception.OutOfBoardException;
import java.util.Arrays;

public enum Rank {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    ZERO(10);

    private final int rank;

    Rank(int rank) {
        this.rank = rank;
    }

    public static Rank findByNumber(int number) {
        if (number == 0) {
            return ZERO;
        }
        return Arrays.stream(Rank.values())
                .filter(rank -> rank.rank == number)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("올바르지 않은 행입니다."));
    }

    public Rank moveRank(MovingPattern pattern) {
        if (pattern.getX() == 1) {
            return moveDown();
        }
        if (pattern.getX() == -1) {
            return moveUp();
        }
        return this;
    }

    public boolean canMoveRank(MovingPattern pattern) {
        return canMoveUp(pattern) && canMoveDown(pattern);
    }

    private boolean canMoveUp(MovingPattern pattern) {
        return !(pattern.getX() == -1 && this == top());
    }

    private boolean canMoveDown(MovingPattern pattern) {
        return !(pattern.getX() == 1 && this == bottom());
    }

    public boolean isBiggerThan(Rank other) {
        return this.rank > other.rank;
    }

    public int getGapBetween(Rank other) {
        return Math.abs(this.rank - other.rank);
    }

    private Rank moveUp() {
        if (this == ONE) {
            throw new OutOfBoardException();
        }
        return findByNumber(this.rank - 1);
    }

    private Rank moveDown() {
        if (this == ZERO) {
            throw new OutOfBoardException();
        }
        return findByNumber(this.rank + 1);
    }

    private Rank top() {
        return ONE;
    }

    private Rank bottom() {
        return ZERO;
    }

    public int value() {
        return rank;
    }
}
