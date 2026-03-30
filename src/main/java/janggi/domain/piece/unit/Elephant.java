package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Movement;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.path.FixedPathStrategy;
import janggi.domain.piece.path.Path;
import janggi.domain.piece.path.PathStrategy;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {
    private static final PieceName NAME = PieceName.ELEPHANT;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Elephant(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        if (path.isEmpty()) {
            return false;
        }
        List<Point> points = path.getPath();

        return points.stream()
                .limit(points.size() - 1)
                .noneMatch(piecesOnPaths::containsKey);
    }

    @Override
    protected List<Movement> createCandidateMovement() {
        List<Movement> directions = new ArrayList<>();

        directions.add(new Movement(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST)));
        directions.add(new Movement(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST)));
        directions.add(new Movement(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST)));
        directions.add(new Movement(List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST)));
        directions.add(new Movement(List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST)));
        directions.add(new Movement(List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST)));
        directions.add(new Movement(List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST)));
        directions.add(new Movement(List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST)));

        return directions;
    }

    @Override
    protected Path refinePath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path.takeLast();
    }
}
