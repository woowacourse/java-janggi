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

public class Soldier extends Piece {
    private static final PieceName NAME = PieceName.SOLDIER;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Soldier(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }

    @Override
    public List<Path> path(Point from) {
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
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path;
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return true;
    }
}
