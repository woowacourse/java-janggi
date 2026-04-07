package janggi.domain.piece.unit;

import java.util.ArrayList;
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

public class Soldier extends Piece {

    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    private static final List<Pattern> CHO_BASE_PATTERNS = List.of(
            new Pattern(List.of(Direction.NORTH)),
            new Pattern(List.of(Direction.WEST)),
            new Pattern(List.of(Direction.EAST))
    );
    private static final List<Pattern> HAN_BASE_PATTERNS = List.of(
            new Pattern(List.of(Direction.SOUTH)),
            new Pattern(List.of(Direction.WEST)),
            new Pattern(List.of(Direction.EAST))
    );

    private static final List<Pattern> ADD_UPPER_PATTERNS = List.of(
            new Pattern(List.of(Direction.NORTH_WEST)),
            new Pattern(List.of(Direction.NORTH_EAST))
    );
    private static final List<Pattern> ADD_LOWER_PATTERNS = List.of(
            new Pattern(List.of(Direction.SOUTH_WEST)),
            new Pattern(List.of(Direction.SOUTH_EAST))
    );

    public Soldier(Side side) {
        super(side, DEFAULT_STRATEGY);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths, Palace palace) {
        return paths.stream()
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> cutPath(path, piecesOnPaths, palace))
                .flatMap(path -> path.getPath().stream())
                .toList();
    }

    @Override
    public List<Pattern> patterns(Point from, Palace palace) {
        List<Pattern> allPatterns = new ArrayList<>();
        List<Pattern> addPatterns = new ArrayList<>();

        palace.getPalacePoint(from).ifPresent(p -> {
            addPatterns.add(p.getPattern());
        });

        if (palace.isInUpper(from)) {
            addPatterns.retainAll(ADD_UPPER_PATTERNS);
            allPatterns.addAll(addPatterns);
        }

        if (palace.isInLower(from)) {
            addPatterns.retainAll(ADD_LOWER_PATTERNS);
            allPatterns.addAll(addPatterns);
        }

        if (Side.CHO.equals(getSide())) {
            allPatterns.addAll(CHO_BASE_PATTERNS);
        }
        if (Side.HAN.equals(getSide())) {
            allPatterns.addAll(HAN_BASE_PATTERNS);
        }
        return allPatterns;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths, Palace palace) {
        return path;
    }
}
