package janggi.domain.piece.unit;

import janggi.domain.board.path.FixedPathStrategy;
import janggi.domain.board.path.Path;
import janggi.domain.board.path.PathStrategy;
import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceName;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {
    private static final PieceName NAME = PieceName.HORSE;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Horse(Side side) {
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
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return paths.stream()
                .map(path -> cutPath(path, piecesOnPaths))
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> path.getPath().getLast())
                .toList();
    }

    @Override
    public List<Pattern> patterns() {
        List<Pattern> directions = new ArrayList<>();

        directions.add(new Pattern(List.of(Direction.NORTH, Direction.NORTH_WEST)));
        directions.add(new Pattern(List.of(Direction.NORTH, Direction.NORTH_EAST)));
        directions.add(new Pattern(List.of(Direction.EAST, Direction.NORTH_EAST)));
        directions.add(new Pattern(List.of(Direction.EAST, Direction.SOUTH_EAST)));
        directions.add(new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_EAST)));
        directions.add(new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_WEST)));
        directions.add(new Pattern(List.of(Direction.WEST, Direction.NORTH_WEST)));
        directions.add(new Pattern(List.of(Direction.WEST, Direction.SOUTH_WEST)));

        return directions;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path;
    }
}
