package janggi.piece;

import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Piece {
    void move();

    List<Position> checkPossibleMoves();

    default void validatePositionRange(Position position) {
        validateRange(position);
    }

    private void validateRange(Position position) {
        if (position.getY() < 0 || position.getY() > 9) {
            throw new IllegalArgumentException("Y 위치가 장기판을 벗어났습니다");
        }
        if (position.getX() < 0 || position.getX() > 8) {
            throw new IllegalArgumentException("X 위치가 장기판을 벗어났습니다");
        }
    }

    default Optional<Position> makePositionWithOptional(Position position) {
        try {
            validateRange(position);
            return Optional.of(position);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
