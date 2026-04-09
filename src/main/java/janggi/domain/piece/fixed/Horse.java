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

public class Horse extends FixedPiece {
    private static final PieceName PIECE_NAME = PieceName.HORSE;
    private static final int PATH_SIZE = 2;
    private static final Score PIECE_SCORE = new Score(5);

    public Horse(Side side) {
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
        List<Movement> directions = new ArrayList<>();

        directions.add(new Movement(List.of(Direction.NORTH, Direction.NORTH_WEST)));
        directions.add(new Movement(List.of(Direction.NORTH, Direction.NORTH_EAST)));
        directions.add(new Movement(List.of(Direction.EAST, Direction.NORTH_EAST)));
        directions.add(new Movement(List.of(Direction.EAST, Direction.SOUTH_EAST)));
        directions.add(new Movement(List.of(Direction.SOUTH, Direction.SOUTH_EAST)));
        directions.add(new Movement(List.of(Direction.SOUTH, Direction.SOUTH_WEST)));
        directions.add(new Movement(List.of(Direction.WEST, Direction.NORTH_WEST)));
        directions.add(new Movement(List.of(Direction.WEST, Direction.SOUTH_WEST)));

        return directions;
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return candidatePath.takeLast();
    }
}
