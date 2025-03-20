package domain;

import domain.pattern.Pattern;
import java.util.List;

public record JanggiPosition(int x, int y) {

    private static final int MAX_ROW_BOUND = 9;
    private static final int MIN_ROW_BOUND = 0;
    private static final int MAX_COLUMN_BOUND = 9;
    private static final int MIN_COLUMN_BOUND = 1;

    public JanggiPosition move(List<Pattern> patterns) {
        JanggiPosition newPosition = this;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
        }
        return newPosition;
    }

    public JanggiPosition moveOnePosition(Pattern pattern) {
        int newX = tranformedNewX(pattern, x);
        int newY = y + pattern.getY();

        return new JanggiPosition(newX, newY);
    }

    private int tranformedNewX(Pattern pattern, int x) {
        int newX = x + pattern.getX();
        if ((pattern.equals(Pattern.UP) && x == 0) || (pattern.equals(Pattern.DIAGONAL_UP_RIGHT) && x == 0) || (
                pattern.equals(Pattern.DIAGONAL_UP_LEFT) && x == 0)) {
            newX = 9;
        }
        if ((pattern.equals(Pattern.DOWN) && x == 9) || (pattern.equals(Pattern.DIAGONAL_DOWN_LEFT) && x == 9) || (
                pattern.equals(Pattern.DIAGONAL_DOWN_RIGHT) && x == 9)) {
            newX = 0;
        }
        return newX;
    }

    public boolean isBiggerXThan(JanggiPosition beforePosition) {
        if (this.x == 0) {
            return true;
        }
        if (beforePosition.x == 0) {
            return false;
        }
        return this.x > beforePosition.x;
    }

    public boolean isBiggerYThan(JanggiPosition beforePosition) {
        return this.y > beforePosition.y;
    }

    public int getXGap(JanggiPosition beforePosition) {
        if (this.x == 0) {
            return 10 - beforePosition.x();
        }

        if (beforePosition.x == 0) {
            return 10 - this.x;
        }

        return Math.abs(this.x - beforePosition.x);
    }

    public int getYGap(JanggiPosition beforePosition) {
        return Math.abs(this.y - beforePosition.y);
    }

    public void validateBound() {
        if (x < MIN_ROW_BOUND || x > MAX_ROW_BOUND || y < MIN_COLUMN_BOUND || y > MAX_COLUMN_BOUND) {
            throw new IllegalArgumentException();
        }
    }
}
