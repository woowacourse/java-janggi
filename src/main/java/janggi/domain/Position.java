package janggi.domain;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        validateBoundary(x, y);
        this.x = x;
        this.y = y;
    }

    private void validateBoundary(int x, int y) {
        if(x < 1 || x > 9 || y < 1 || y > 10) {
            throw new IllegalArgumentException("[ERROR] 보드 범위를 벗어났습니다.");
        }
    }
}
