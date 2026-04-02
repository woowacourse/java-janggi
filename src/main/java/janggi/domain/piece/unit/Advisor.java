package janggi.domain.piece.unit;

import janggi.domain.path.CandidatePath;
import janggi.domain.path.Movement;
import janggi.domain.path.generator.FixedPathStrategy;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Advisor extends Piece {
    private static final PieceName NAME = PieceName.ADVISOR;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();
    private static final Score SCORE = new Score(3);

    public Advisor(Side side) {
        super(NAME, side, DEFAULT_STRATEGY, SCORE);
    }

    @Override
    public List<Movement> createCandidateMovement() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return candidatePath;
    }
}
