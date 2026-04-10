package janggi.domain.piece.stepped;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.Score;
import janggi.domain.piece.path.Direction;
import janggi.domain.piece.path.Movement;
import janggi.domain.side.Side;
import java.util.List;

public class Horse extends SteppedPiece {
    private static final PieceType PIECE_NAME = PieceType.HORSE;
    private static final Score PIECE_SCORE = new Score(5);
    private static final List<Movement> MOVEMENTS = createMovements();

    public Horse(Side side) {
        super(PIECE_NAME, side, PIECE_SCORE);
    }

    private static List<Movement> createMovements() {
        return List.of(
                new Movement(List.of(Direction.NORTH, Direction.NORTH_WEST)),
                new Movement(List.of(Direction.NORTH, Direction.NORTH_EAST)),
                new Movement(List.of(Direction.EAST, Direction.NORTH_EAST)),
                new Movement(List.of(Direction.EAST, Direction.SOUTH_EAST)),
                new Movement(List.of(Direction.SOUTH, Direction.SOUTH_EAST)),
                new Movement(List.of(Direction.SOUTH, Direction.SOUTH_WEST)),
                new Movement(List.of(Direction.WEST, Direction.NORTH_WEST)),
                new Movement(List.of(Direction.WEST, Direction.SOUTH_WEST))
        );
    }

    @Override
    protected List<Movement> getMovements() {
        return MOVEMENTS;
    }
}
