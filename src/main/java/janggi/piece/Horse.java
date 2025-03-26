package janggi.piece;

import static janggi.piece.direction.Direction.DOWN;
import static janggi.piece.direction.Direction.DOWN_LEFT;
import static janggi.piece.direction.Direction.DOWN_RIGHT;
import static janggi.piece.direction.Direction.LEFT;
import static janggi.piece.direction.Direction.RIGHT;
import static janggi.piece.direction.Direction.UP;
import static janggi.piece.direction.Direction.UP_LEFT;
import static janggi.piece.direction.Direction.UP_RIGHT;

import janggi.piece.direction.Movement;
import janggi.position.Position;
import java.util.List;

public class Horse extends NonIterablePiece {

    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(DOWN, DOWN_RIGHT),
            new Movement(DOWN, DOWN_LEFT),
            new Movement(UP, UP_RIGHT),
            new Movement(UP, UP_LEFT),
            new Movement(RIGHT, DOWN_RIGHT),
            new Movement(LEFT, DOWN_LEFT),
            new Movement(RIGHT, UP_RIGHT),
            new Movement(LEFT, UP_LEFT)
    );

    public Horse(final Team team, final Position currentPosition) {
        super(team, currentPosition);
    }

    @Override
    protected List<Movement> getMovements() {
        return MOVEMENTS;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.HORSE;
    }
}
