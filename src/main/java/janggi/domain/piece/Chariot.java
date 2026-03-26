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

public class Chariot extends Piece {
    private static final PieceName NAME = PieceName.CHARIOT;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Chariot(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }

    @Override
    public List<Path> path(Point from) {
        List<Path> paths = new ArrayList<>();
        paths.add(convertToPath(List.of(Direction.NORTH), from));
        paths.add(convertToPath(List.of(Direction.SOUTH), from));
        paths.add(convertToPath(List.of(Direction.WEST), from));
        paths.add(convertToPath(List.of(Direction.EAST), from));

        return paths;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return false;
    }
}
