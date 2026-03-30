package janggi.domain.piece.unit;

import janggi.domain.board.path.LinearPathStrategy;
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

public class Chariot extends Piece {
    private static final PieceName NAME = PieceName.CHARIOT;
    private static final PathStrategy DEFAULT_STRATEGY = new LinearPathStrategy();

    public Chariot(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return paths.stream()
                .map(path -> cutPath(path, piecesOnPaths))
                .filter(path -> isValidPath(path, piecesOnPaths))
                .flatMap(path -> path.getPath().stream())
                .toList();
    }

    @Override
    public List<Pattern> patterns() {
        List<Pattern> paths = new ArrayList<>();
        paths.add(new Pattern(List.of(Direction.NORTH)));
        paths.add(new Pattern(List.of(Direction.SOUTH)));
        paths.add(new Pattern(List.of(Direction.WEST)));
        paths.add(new Pattern(List.of(Direction.EAST)));

        return paths;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path.getPath().stream()
                .filter(piecesOnPaths::containsKey)
                .findFirst()
                .map(path::takeUntil)
                .orElse(path);
    }
}
