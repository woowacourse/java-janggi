package janggi.piece;

import janggi.position.Position;

import java.util.List;
import java.util.Optional;

public interface Piece {
    void move();

    List<Position> checkPossibleMoves();

    default Optional<Position> checkOutOfBoundsPosition(Position position) {
        try {
            return Optional.of(position);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
