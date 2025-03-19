package domain;

import domain.pattern.Pattern;
import java.util.List;

public record Position(int x, int y) {
    public Position move(List<Pattern> patterns) {
        Position newPosition = this;
        for (Pattern pattern : patterns) {
            newPosition = newPosition.moveOnePosition(pattern);
        }
        return newPosition;
    }

    public Position moveOnePosition(Pattern pattern) {
        int newX = tranformedNewX(pattern, x);
        int newY = y + pattern.getY();

        return new Position(newX, newY);
    }

    private int tranformedNewX(Pattern pattern, int x) {
        int newX = x + pattern.getX();
        if (pattern.equals(Pattern.UP) && x == 0) {
            newX = 9;
        }
        if (pattern.equals(Pattern.DIAGONAL_UP_RIGHT) && x == 0) {
            newX = 9;
        }
        if (pattern.equals(Pattern.DIAGONAL_UP_LEFT) && x == 0) {
            newX = 9;
        }
        if (pattern.equals(Pattern.DOWN) && x == 9) {
            newX = 0;
        }
        if (pattern.equals(Pattern.DIAGONAL_DOWN_LEFT) && x == 9) {
            newX = 0;
        }
        if (pattern.equals(Pattern.DIAGONAL_DOWN_RIGHT) && x == 9) {
            newX = 0;
        }
        return newX;
    }

    public boolean isBiggerXThan(Position beforePosition) {
        if (this.x == 0) {
            return true;
        }
        if (beforePosition.x == 0) {
            return false;
        }
        if (this.x > beforePosition.x) {
            return true;
        }
        return false;
    }

    public boolean isBiggerYThan(Position beforePosition) {
        return this.y > beforePosition.y;
    }

    public int getXGap(Position beforePosition) {
        if (this.x == 0) {
            return 10 - beforePosition.x();
        }

        if (beforePosition.x == 0) {
            return 10 - this.x;
        }

        return Math.abs(this.x - beforePosition.x);
    }

    public int getYGap(Position beforePosition) {
        return Math.abs(this.y - beforePosition.y);
    }
}
