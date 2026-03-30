package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.path.FixedPathStrategy;
import janggi.domain.piece.path.Path;
import janggi.domain.piece.path.PathStrategy;
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
    protected List<Pattern> createCandidatePattern() {
        List<Pattern> paths = new ArrayList<>();
        for (Direction value : Direction.values()) {
            Pattern path = new Pattern(List.of(value));
            paths.add(path);
        }

        return paths;
    }


    @Override
    protected Path refinePath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path;
    }
}
