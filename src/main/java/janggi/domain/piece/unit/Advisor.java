package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Movement;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.piece.path.FixedPathStrategy;
import janggi.domain.piece.path.PathStrategy;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Advisor extends Piece {
    private static final PieceName NAME = PieceName.ADVISOR;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Advisor(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
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
