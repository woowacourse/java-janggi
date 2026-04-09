package janggi.domain.piece.stepped;

import janggi.domain.path.CandidatePath;
import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;

public class Horse extends SteppedPiece {
    private static final PieceName PIECE_NAME = PieceName.HORSE;
    private static final int PATH_SIZE = 2;
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
    public List<Movement> getMovements() {
        return MOVEMENTS;
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return candidatePath.takeLast();
    }
}
