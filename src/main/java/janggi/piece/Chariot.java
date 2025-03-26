package janggi.piece;

import static janggi.piece.direction.Direction.DOWN;
import static janggi.piece.direction.Direction.LEFT;
import static janggi.piece.direction.Direction.RIGHT;
import static janggi.piece.direction.Direction.UP;

import janggi.piece.direction.Movement;
import janggi.position.Position;
import java.util.List;

public class Chariot extends IterablePiece {

    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(UP),
            new Movement(DOWN),
            new Movement(RIGHT),
            new Movement(LEFT)
    );

    public Chariot(final Team team, final Position currentPosition) {
        super(team, currentPosition);
    }

    @Override
    protected List<Movement> getMovements() {
        return MOVEMENTS;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CHARIOT;
    }
}
