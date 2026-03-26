package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.coordinate.Direction;
import janggi.domain.coordinate.LinearPathStrategy;
import janggi.domain.coordinate.Path;
import janggi.domain.coordinate.PathStrategy;
import janggi.domain.coordinate.Point;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {
    private static final PieceName NAME = PieceName.CANNON;
    private static final PathStrategy DEFAULT_STRATEGY = new LinearPathStrategy();

    public Cannon(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(Point from, Point to, Board board) {
        return List.of();
    }

    @Override
    protected List<Path> path(Point from) {
        List<Path> paths = new ArrayList<>();
        paths.add(convertToPath(List.of(Direction.NORTH), from));
        paths.add(convertToPath(List.of(Direction.SOUTH), from));
        paths.add(convertToPath(List.of(Direction.WEST), from));
        paths.add(convertToPath(List.of(Direction.EAST), from));

        return paths;
    }

    @Override
    protected List<Path> filterPath(Path path, Board board) {
        return List.of();
    }
}
