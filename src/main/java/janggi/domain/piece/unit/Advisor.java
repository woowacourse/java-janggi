package janggi.domain.piece.unit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import janggi.domain.board.Palace;
import janggi.domain.board.coordinate.FixedPathStrategy;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceType;
import janggi.domain.side.Side;

public class Advisor extends Piece {

    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    private static final List<Pattern> BASE_PATTERNS = Arrays.stream(Direction.values())
            .map(direction -> new Pattern(List.of(direction)))
            .toList();

    public Advisor(Side side) {
        super(side, DEFAULT_STRATEGY);
    }

    @Override
    public PieceType getType() {
        return PieceType.ADVISOR;
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths, Palace palace) {
        return paths.stream()
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> cutPath(path, piecesOnPaths, palace))
                .map(path -> path.getPath().getLast())
                .filter(point -> palace.isInPalace(point))
                .toList();
    }

    @Override
    public List<Pattern> patterns(Point from, Palace palace) {
        return BASE_PATTERNS;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths, Palace palace) {
        return path;
    }
}
