package janggi.domain.piece.unit;

import janggi.domain.board.coordinate.FixedPathStrategy;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceType;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {
    private static final PieceType TYPE = PieceType.HORSE;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();
    private static final List<Pattern> PATTERNS = List.of(
            new Pattern(List.of(Direction.NORTH, Direction.NORTH_WEST)),
            new Pattern(List.of(Direction.NORTH, Direction.NORTH_EAST)),
            new Pattern(List.of(Direction.EAST, Direction.NORTH_EAST)),
            new Pattern(List.of(Direction.EAST, Direction.SOUTH_EAST)),
            new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_EAST)),
            new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_WEST)),
            new Pattern(List.of(Direction.WEST, Direction.NORTH_WEST)),
            new Pattern(List.of(Direction.WEST, Direction.SOUTH_WEST))
    );

    public Horse(Side side) {
        super(TYPE, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return paths.stream()
                .map(path -> cutPath(path, piecesOnPaths))
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> path.getPath().getLast())
                .toList();
    }

    @Override
    public List<Pattern> patterns() {
        return PATTERNS;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path;
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        if (path.isEmpty()) {
            return false;
        }
        List<Point> points = path.getPath();

        return points.stream()
                .limit(points.size() - 1)
                .noneMatch(piecesOnPaths::containsKey);
    }
}
