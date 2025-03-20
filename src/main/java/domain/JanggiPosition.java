package domain;

import domain.pattern.Pattern;
import java.util.List;

public record JanggiPosition(int file, int rank) {

    private static final int MAX_ROW_BOUND = 9;
    private static final int MIN_ROW_BOUND = 0;
    private static final int MAX_COLUMN_BOUND = 9;
    private static final int MIN_COLUMN_BOUND = 1;

    public JanggiPosition move(final List<Pattern> patterns) {
        JanggiPosition newPosition = this;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
        }
        return newPosition;
    }

    public JanggiPosition moveOnePosition(final Pattern pattern) {
        int newX = transformedNewX(pattern, file);
        int newY = rank + pattern.getY();

        return new JanggiPosition(newX, newY);
    }

    private int transformedNewX(final Pattern pattern, int x) {
        int newX = x + pattern.getX();
        if ((pattern.equals(Pattern.MOVE_UP) && x == MIN_ROW_BOUND) || (pattern.equals(Pattern.MOVE_DIAGONAL_UP_RIGHT)
                && x == MIN_ROW_BOUND) || (
                pattern.equals(Pattern.MOVE_DIAGONAL_UP_LEFT) && x == MIN_ROW_BOUND)) {
            newX = 9;
        }
        if ((pattern.equals(Pattern.MOVE_DOWN) && x == MAX_ROW_BOUND) || (pattern.equals(Pattern.MOVE_DIAGONAL_DOWN_LEFT)
                && x == MAX_ROW_BOUND) || (
                pattern.equals(Pattern.MOVE_DIAGONAL_DOWN_RIGHT) && x == MAX_ROW_BOUND)) {
            newX = 0;
        }
        return newX;
    }

    public boolean isBiggerXThan(final JanggiPosition beforePosition) {
        if (this.file == 0) {
            return true;
        }
        if (beforePosition.file == 0) {
            return false;
        }
        return this.file > beforePosition.file;
    }

    public boolean isBiggerYThan(final JanggiPosition beforePosition) {
        return this.rank > beforePosition.rank;
    }

    public int getXGap(final JanggiPosition beforePosition) {
        if (this.file == 0) {
            return 10 - beforePosition.file();
        }

        if (beforePosition.file == 0) {
            return 10 - this.file;
        }

        return Math.abs(this.file - beforePosition.file);
    }

    public int getYGap(final JanggiPosition beforePosition) {
        return Math.abs(this.rank - beforePosition.rank);
    }

    public void validateBound() {
        if (file < MIN_ROW_BOUND || file > MAX_ROW_BOUND || rank < MIN_COLUMN_BOUND || rank > MAX_COLUMN_BOUND) {
            throw new IllegalArgumentException("보드판을 넘어서 이동할 수 없습니다.");
        }
    }
}
