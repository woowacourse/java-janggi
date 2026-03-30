package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.path.LinearPathStrategy;
import janggi.domain.piece.path.Path;
import janggi.domain.piece.path.PathStrategy;
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
    protected List<Pattern> createCandidatePattern() {
        List<Pattern> paths = new ArrayList<>();
        paths.add(new Pattern(List.of(Direction.NORTH)));
        paths.add(new Pattern(List.of(Direction.SOUTH)));
        paths.add(new Pattern(List.of(Direction.WEST)));
        paths.add(new Pattern(List.of(Direction.EAST)));

        return paths;
    }

    @Override
    protected Path refinePath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path.getPath().stream()
                .filter(piecesOnPaths::containsKey)
                .findFirst()
                .map(path::takeUntil)
                .orElse(path);
    }
}
