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

    public Cannon(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }

    @Override
    public List<Pattern> directions() {
        List<Pattern> paths = new ArrayList<>();
        paths.add(new Pattern(List.of(Direction.NORTH), pathStrategy));
        paths.add(new Pattern(List.of(Direction.SOUTH), pathStrategy));
        paths.add(new Pattern(List.of(Direction.WEST), pathStrategy));
        paths.add(new Pattern(List.of(Direction.EAST), pathStrategy));

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
