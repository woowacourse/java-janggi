package domain.position;

import domain.Pattern;
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

    public Rank moveRank(Pattern pattern) {
        if (pattern.getX() == 1) {
            return moveDown();
        }
        if (pattern.getX() == -1) {
            return moveUp();
        }
        return this;
    }

    public boolean canMoveRank(Pattern pattern) {
        if ((pattern.getX() == -1 && this == top()) || (pattern.getX() == 1 && this == bottom())) {
            return false;
        }
        return true;
    }

    public boolean isBiggerThan(Rank other) {
        return this.rank > other.rank;
    }

    public int getGapBetween(Rank other) {
        return Math.abs(this.rank - other.rank);
    }

    private Rank moveUp() {
        if (this == ONE) {
            throw new IllegalStateException("더이상 위로 이동할 수 없습니다.");
        }
        return findByNumber(this.rank - 1);
    }

    private Rank moveDown() {
        if (this == ZERO) {
            throw new IllegalStateException("더이상 아래로 이동할 수 없습니다.");
        }
        return findByNumber(this.rank + 1);
    }

    private Rank top() {
        return ONE;
    }

    private Rank bottom() {
        return ZERO;
    }
}
