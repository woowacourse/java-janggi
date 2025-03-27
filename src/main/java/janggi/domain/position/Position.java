package janggi.domain.position;

import static janggi.domain.Board.MAX_X_POSITION;
import static janggi.domain.Board.MAX_Y_POSITION;
import static janggi.domain.Board.MIN_POSITION;

public record Position(int x, int y) {

    public Position {
        validatePosition(x, y);
    }

    public boolean isPalace() {
        return isXInPalace() && isYInPalace();
    }

    private boolean isXInPalace() {
        return x >= 3 && x <= 5;
    }

    private boolean isYInPalace() {
        return (y >= 0 && y <= 2) || (y >= 7 && y <= 9);
    }

    private void validatePosition(final int x, final int y) {
        if (isInvalidPosition(x, y)) {
            throw new IllegalArgumentException("보드를 벗어났습니다.");
        }
    }

    private boolean isInvalidPosition(final int x, final int y) {
        return x < MIN_POSITION || x > MAX_X_POSITION || y < MIN_POSITION || y > MAX_Y_POSITION;
    }

    public RawPosition up() {
        return new RawPosition(x, y + 1);
    }

    public RawPosition down() {
        return new RawPosition(x, y - 1);
    }

    public RawPosition left() {
        return new RawPosition(x - 1, y);
    }

    public RawPosition right() {
        return new RawPosition(x + 1, y);
    }

    public RawPosition upRightDiagonal() {
        return new RawPosition(x + 1, y + 1);
    }

    public RawPosition downRightDiagonal() {
        return new RawPosition(x + 1, y - 1);
    }

    public RawPosition upLeftDiagonal() {
        return new RawPosition(x - 1, y + 1);
    }

    public RawPosition downLeftDiagonal() {
        return new RawPosition(x - 1, y - 1);
    }
}
