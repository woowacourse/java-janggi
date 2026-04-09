package janggi.domain.piece.fixed;

import janggi.domain.path.CandidatePath;
import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends FixedPiece {
    private static final PieceName PIECE_NAME = PieceName.ELEPHANT;
    private static final int PATH_SIZE = 3;
    private static final Score PIECE_SCORE = new Score(3);

    public Elephant(Side side) {
        super(PIECE_NAME, side, PIECE_SCORE);
    }

    @Override
    protected boolean isValidPath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        if (candidatePath.isEmpty() || candidatePath.getPath().size() != PATH_SIZE) {
            return false;
        }
        List<Point> points = candidatePath.getPath();

        return points.stream()
                .limit(points.size() - 1)
                .noneMatch(piecesOnPaths::containsKey);
    }

    @Override
    public List<Movement> getMovements() {
        List<Movement> movements = new ArrayList<>();

        movements.add(new Movement(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST)));
        movements.add(new Movement(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST)));
        movements.add(new Movement(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST)));
        movements.add(new Movement(List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST)));
        movements.add(new Movement(List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST)));
        movements.add(new Movement(List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST)));
        movements.add(new Movement(List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST)));
        movements.add(new Movement(List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST)));

        return movements;
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return candidatePath.takeLast();
    }
}
