package janggi.domain.piece.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import janggi.domain.board.Palace;
import janggi.domain.board.coordinate.LinearPathStrategy;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceType;
import janggi.domain.side.Side;

public class Cannon extends Piece {

    private static final PathStrategy DEFAULT_STRATEGY = new LinearPathStrategy();

    private static final List<Pattern> BASE_PATTERNS = List.of(
            new Pattern(List.of(Direction.NORTH)),
            new Pattern(List.of(Direction.SOUTH)),
            new Pattern(List.of(Direction.WEST)),
            new Pattern(List.of(Direction.EAST))
    );

    public Cannon(Side side) {
        super(side, DEFAULT_STRATEGY);
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths, Palace palace) {
        return paths.stream()
                .map(path -> cutPath(path, piecesOnPaths, palace))
                .filter(path -> isValidPath(path, piecesOnPaths))
                .flatMap(path -> path.getPath().stream()
                        .filter(p -> !(path.isDiagonal() && !palace.isInPalace(p)))
                )
                .toList();
    }

    @Override
    public List<Pattern> patterns(Point from, Palace palace) {
        List<Pattern> addPatterns = new ArrayList<>();

        palace.getPalacePoint(from).ifPresent(p -> {
            addPatterns.add(p.getPattern());
        });

        addPatterns.addAll(BASE_PATTERNS);
        return addPatterns;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths, Palace palace) {
        List<Point> points = path.getPath();
        int count = 0;
        List<Point> cutPoints = new ArrayList<>();

        for (Point point : points) {
            if (piecesOnPaths.getOrDefault(point, Empty.INSTANCE) instanceof Cannon) {
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
        return new Path(cutPoints, path.isDiagonal());
    }
}
