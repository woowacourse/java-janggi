package janggi.position;

import janggi.exception.ErrorException;
import java.util.Objects;

public final class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(String xy) {
        if (xy.length() != 2) {
            throw new ErrorException("잘못된 좌표 입력입니다.");
        }
        try {
            String[] split = xy.split("", -1);
            this.x = Integer.parseInt(split[0]);
            this.y = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            throw new ErrorException("숫자만 입력 가능합니다.");
        }
    }

    public boolean isHorizontal(Position otherPosition) {
        return this.y == otherPosition.y;
    }

    public boolean isVertical(Position otherPosition) {
        return this.x == otherPosition.x;
    }

    public int calculateXDistance(Position otherPosition) {
        return Math.abs(this.x - otherPosition.x);
    }

    public int calculateYDistance(Position otherPosition) {
        return Math.abs(this.y - otherPosition.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object otherPosition) {
        if (otherPosition == null || getClass() != otherPosition.getClass()) {
            return false;
        }
        Position position = (Position) otherPosition;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
