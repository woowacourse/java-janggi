package janggi.domain.piece.unit;

import janggi.domain.board.coordinate.FixedPathStrategy;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Directions;
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
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return paths.stream()
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> cutPath(path, piecesOnPaths))
                .map(path -> path.getPath().getLast())
                .toList();
    }

    @Override
    public List<Directions> directions() {
        List<Directions> directions = new ArrayList<>();

        directions.add(new Directions (List.of(Direction.NORTH, Direction.NORTH_WEST), pathStrategy));
        directions.add(new Directions(List.of(Direction.NORTH, Direction.NORTH_EAST), pathStrategy));
        directions.add(new Directions(List.of(Direction.EAST, Direction.NORTH_EAST), pathStrategy));
        directions.add(new Directions(List.of(Direction.EAST, Direction.SOUTH_EAST), pathStrategy));
        directions.add(new Directions(List.of(Direction.SOUTH, Direction.SOUTH_EAST), pathStrategy));
        directions.add(new Directions(List.of(Direction.SOUTH, Direction.SOUTH_WEST), pathStrategy));
        directions.add(new Directions(List.of(Direction.WEST, Direction.NORTH_WEST), pathStrategy));
        directions.add(new Directions(List.of(Direction.WEST, Direction.SOUTH_WEST), pathStrategy));

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
