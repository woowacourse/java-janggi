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

public class Soldier extends Piece {
    private static final PieceName NAME = PieceName.SOLDIER;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Soldier(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(Point from, Point to, Board board) {
        return List.of();
    }

    @Override
    protected List<Path> path(Point from) {
        List<Path> directions = new ArrayList<>();
        if (Side.HAN.equals(side)) {
            directions.add(convertToPath(List.of(Direction.NORTH), from));
            directions.add(convertToPath(List.of(Direction.WEST), from));
            directions.add(convertToPath(List.of(Direction.EAST), from));
        }
        if (Side.CHO.equals(side)) {
            directions.add(convertToPath(List.of(Direction.SOUTH), from));
            directions.add(convertToPath(List.of(Direction.WEST), from));
            directions.add(convertToPath(List.of(Direction.EAST), from));
        }
        return directions;
    }

    @Override
    protected List<Path> filterPath(Path path, Board board) {
        return List.of();
    }
}
