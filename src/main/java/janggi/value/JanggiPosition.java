package janggi.value;

import java.util.Objects;

public final class JanggiPosition implements Comparable<JanggiPosition> {
    private final static int X_MIN = 0;
    private final static int X_MAX = 8;
    private final static int Y_MIN = 0;
    private final static int Y_MAX = 9;

    private final int x;
    private final int y;

    public JanggiPosition(final int x, final int y) {
        validatePositionInRange(x,y);
        this.x = x;
        this.y = y;
    }

    private void validatePositionInRange(int x, int y) {
        if (x < X_MIN || x > X_MAX || y < Y_MIN || y > Y_MAX) {
            throw new IllegalArgumentException("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
        }
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(JanggiPosition otherJanggiPosition) {
        if (y == otherJanggiPosition.getY()) {
            return Integer.compare(otherJanggiPosition.getX(), x);
        }
        return Integer.compare(otherJanggiPosition.getY(), y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        JanggiPosition janggiPosition = (JanggiPosition) object;
        return x == janggiPosition.x && y == janggiPosition.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
