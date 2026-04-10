package janggi.domain.piece.unit;

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

public class Elephant extends Piece {

    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    private static final List<Pattern> BASE_PATTERNS = List.of(
            new Pattern(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST)),
            new Pattern(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST)),
            new Pattern(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST)),
            new Pattern(List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST)),
            new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST)),
            new Pattern(List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST)),
            new Pattern(List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST)),
            new Pattern(List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST))
    );

    public Elephant(Side side) {
        super(side, DEFAULT_STRATEGY);
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths, Palace palace) {
        return paths.stream()
                .map(path -> cutPath(path, piecesOnPaths, palace))
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> path.getPath().getLast())
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
