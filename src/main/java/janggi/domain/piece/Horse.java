package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.coodinate.Direction;
import janggi.domain.coodinate.FixedPathStrategy;
import janggi.domain.coodinate.Path;
import janggi.domain.coodinate.PathStrategy;
import janggi.domain.coodinate.Point;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {
    private static final PieceName NAME = PieceName.HORSE;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Horse(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(Point from, Point to, Board board) {
        return List.of();
    }

    @Override
    protected List<Path> path(Point from) {
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
    protected List<Path> filterPath(Path path, Board board) {
        return List.of();
    }
}
