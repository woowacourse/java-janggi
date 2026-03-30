package janggi.domain.piece.unit;

import janggi.domain.board.path.FixedPathStrategy;
import janggi.domain.board.path.Path;
import janggi.domain.board.path.PathStrategy;
import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceName;
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
        return paths.stream()
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> cutPath(path, piecesOnPaths))
                .map(path -> path.getPath().getLast())
                .toList();
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
        return path;
    }
}
