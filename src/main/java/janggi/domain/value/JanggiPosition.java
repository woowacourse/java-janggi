package janggi.domain.value;

public record JanggiPosition(int x, int y) {
    private final static int X_MIN = 0;
    private final static int X_MAX = 8;
    private final static int Y_MIN = 0;
    private final static int Y_MAX = 9;

    public JanggiPosition {
        validatePositionInRange(x, y);
    }

    private void validatePositionInRange(int x, int y) {
        if (x < X_MIN || x > X_MAX || y < Y_MIN || y > Y_MAX) {
            throw new IllegalArgumentException("[ERROR] x좌표는 0~8, y좌표는 0~9 사이로 입력해주세요.");
        }
    }

    public boolean isPositionInCastle() {
        boolean isInHorizontalRange = x >= 3 && x <= 5;
        boolean isInHanPalace = y >= 0 && y <= 2;
        boolean isInChoPalace = y >= 7 && y <= 9;

        return isInHorizontalRange && (isInHanPalace || isInChoPalace);
    }

    public boolean isDiagonalPositionInCastle() {
        boolean isCornerPosition = (x == 3 || x == 5) && (y == 0 || y == 2 || y == 7 || y == 9);

        boolean isCenterDiagonal = x == 4 && (y == 1 || y == 8);

        return isCornerPosition || isCenterDiagonal;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
