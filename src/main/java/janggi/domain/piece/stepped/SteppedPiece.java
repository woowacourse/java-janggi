package janggi.domain.piece.stepped;

import janggi.domain.path.CandidatePath;
import janggi.domain.path.generator.FixedPathStrategy;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;

public abstract class SteppedPiece extends Piece {
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    protected SteppedPiece(PieceName name, Side side, Score score) {
        super(name, side, DEFAULT_STRATEGY, score);
    }

    @Override
    protected boolean isValidPath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        List<Point> points = candidatePath.getPath();

        return points.stream()
                .limit(points.size() - 1)
                .noneMatch(piecesOnPaths::containsKey);
    }
}
