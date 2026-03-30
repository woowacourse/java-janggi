package domain;

import java.util.Objects;

public class Position {

    public static int MAX_X_VALUE = 8;
    public static int MIN_X_VALUE = 0;
    public static int MAX_Y_VALUE = 9;
    public static int MIN_Y_VALUE = 0;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validatePosX(x);
        validatePosY(y);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void validatePosX(int x) {
        if (x < 0 || x > 8) {
            throw new IllegalArgumentException("[ERROR] x 좌표는 0~8 사이어야합니다.");
        }
    }

    private void validatePosY(int y) {
        if (y < 0 || y > 9) {
            throw new IllegalArgumentException("[ERROR] y 좌표는 0~9 사이어야합니다.");
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
