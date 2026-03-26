package janggi.domain.piece;

import janggi.domain.coordinate.Direction;
import janggi.domain.coordinate.FixedPathStrategy;
import janggi.domain.coordinate.Path;
import janggi.domain.coordinate.PathStrategy;
import janggi.domain.coordinate.Point;
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
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return paths.stream()
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> cutPath(path, piecesOnPaths))
                .map(path -> path.getPath().getLast())
                .toList();
    }

    @Override
    public List<Path> path(Point from) {
        List<Path> directions = new ArrayList<>();

        directions.add(convertToPath(List.of(Direction.NORTH, Direction.NORTH_WEST), from));
        directions.add(convertToPath(List.of(Direction.NORTH, Direction.NORTH_EAST), from));

        directions.add(convertToPath(List.of(Direction.EAST, Direction.NORTH_EAST), from));
        directions.add(convertToPath(List.of(Direction.EAST, Direction.SOUTH_EAST), from));

        directions.add(convertToPath(List.of(Direction.SOUTH, Direction.SOUTH_EAST), from));
        directions.add(convertToPath(List.of(Direction.SOUTH, Direction.SOUTH_WEST), from));

        directions.add(convertToPath(List.of(Direction.WEST, Direction.NORTH_WEST), from));
        directions.add(convertToPath(List.of(Direction.WEST, Direction.SOUTH_WEST), from));

        return directions;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path;
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        List<Point> points = path.getPath();

        return points.stream()
                .limit(points.size()-1)
                .noneMatch(piecesOnPaths::containsKey);
    }
}
