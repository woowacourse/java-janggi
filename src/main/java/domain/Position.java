package domain;

import java.util.Objects;

public class Position {

    public final static int MAX_X_VALUE = 8;
    public final static int MIN_X_VALUE = 0;
    public final static int MAX_Y_VALUE = 9;
    public final static int MIN_Y_VALUE = 0;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validatePosX(x);
        validatePosY(y);
        this.x = x;
        this.y = y;
    }

    public boolean canMove(Direction direction) {
        int dx = direction.getDx();
        int dy = direction.getDy();
        return ((MIN_X_VALUE <= x + dx && x + dx <= MAX_X_VALUE)
                && (MIN_Y_VALUE <= y + dy && y + dy <= MAX_Y_VALUE));
    }

    public Position move(Direction direction) {
        int dx = direction.getDx();
        int dy = direction.getDy();
        return new Position(x + dx, y + dy);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void validatePosX(int x) {
        if (x < MIN_X_VALUE || x > MAX_X_VALUE) {
            throw new IllegalArgumentException("[ERROR] x 좌표는 " + MIN_X_VALUE + "~" + MAX_X_VALUE + "사이어야합니다." + " 입력값: " + x);
        }
    }

    private void validatePosY(int y) {
        if (y < MIN_Y_VALUE || y > MAX_Y_VALUE) {
            throw new IllegalArgumentException("[ERROR] y 좌표는 " + MIN_Y_VALUE + "~" + MAX_Y_VALUE + "사이어야합니다." + " 입력값: " + y);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
