package janggi.domain.piece.unit;

import janggi.domain.board.coordinate.FixedPathStrategy;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceName;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Advisor extends Piece {
    private static final PieceName NAME = PieceName.ADVISOR;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Advisor(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }

    @Override
    public List<Pattern> patterns() {
        List<Pattern> paths = new ArrayList<>();
        for (Direction value : Direction.values()) {
            Pattern path = new Pattern(List.of(value));
            paths.add(path);
        }

        return paths;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return null;
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return false;
    }
}
