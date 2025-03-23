package janggi.domain.piece.direction;

import static janggi.domain.piece.direction.BoardSize.isInBoard;

public record Position(int x, int y) {

    public Position {
        validatePosition(x, y);
    }

    private void validatePosition(final int x, final int y) {
        if (isInBoard(x, y)) {
            return;
        }
        throw new IllegalArgumentException("보드를 벗어났습니다.");
    }
}
