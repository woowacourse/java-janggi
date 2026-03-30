package janggi.domain.piece.unit;

import janggi.domain.board.coordinate.LinearPathStrategy;
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

public class Cannon extends Piece {
    private static final PieceName NAME = PieceName.CANNON;
    private static final PathStrategy DEFAULT_STRATEGY = new LinearPathStrategy();
    private static final List<Pattern> PATTERNS = List.of(
            new Pattern(List.of(Direction.NORTH)),
            new Pattern(List.of(Direction.SOUTH)),
            new Pattern(List.of(Direction.WEST)),
            new Pattern(List.of(Direction.EAST))
    );

    public Cannon(Side side) {
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
        return PATTERNS;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        List<Point> points = path.getPath();
        int count = 0;
        List<Point> cutPoints = new ArrayList<>();

        for (Point point : points) {
            if (piecesOnPaths.getOrDefault(point, new Empty()) instanceof Cannon) {
                break;
            }
            if (piecesOnPaths.containsKey(point)) {
                count++;
                if (count == 1) {
                    continue;
                }
            }
            if (count >= 1) {
                cutPoints.add(point);
            }
            if (count == 2) {
                break;
            }
        }
        return new Path(cutPoints);
    }
}
