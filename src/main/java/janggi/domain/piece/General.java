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

public class General extends Piece {
    private static final PieceName NAME = PieceName.GENERAL;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public General(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }

    @Override
    public List<Path> path(Point from) {
        List<Path> paths = new ArrayList<>();
        for (Direction value : Direction.values()) {
            Path path = convertToPath(List.of(value), from);
            paths.add(path);
        }

        return paths;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }
}
