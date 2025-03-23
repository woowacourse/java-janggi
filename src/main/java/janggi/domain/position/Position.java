package janggi.domain.position;

import static janggi.domain.Board.MAX_X_POSITION;
import static janggi.domain.Board.MAX_Y_POSITION;
import static janggi.domain.Board.MIN_POSITION;

public record Position(int x, int y) {

    public Position {
        validatePosition(x, y);
    }

    private void validatePosition(final int x, final int y) {
        if (isInvalidPosition(x, y)) {
            throw new IllegalArgumentException("보드를 벗어났습니다.");
        }
    }

    private boolean isInvalidPosition(final int x, final int y) {
        return x < MIN_POSITION || x > MAX_X_POSITION || y < MIN_POSITION || y > MAX_Y_POSITION;
    }
}
