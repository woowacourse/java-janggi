package janggi.domain.piece.unit;

import janggi.domain.board.coordinate.FixedPathStrategy;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.PieceName;
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
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }

    @Override
    public List<Path> path(Point from) {
        List<Path> directions = new ArrayList<>();

        directions.add(convertToPath(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST), from));
        directions.add(convertToPath(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST), from));

        directions.add(convertToPath(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST), from));
        directions.add(convertToPath(List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST), from));

        directions.add(convertToPath(List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST), from));
        directions.add(convertToPath(List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST), from));

        directions.add(convertToPath(List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST), from));
        directions.add(convertToPath(List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST), from));

        return directions;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }
}
