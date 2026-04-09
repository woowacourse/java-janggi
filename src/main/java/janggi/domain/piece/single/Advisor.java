package janggi.domain.piece.single;

import janggi.domain.path.CandidatePath;
import janggi.domain.path.Movement;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.piece.stepped.SteppedPiece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Advisor extends SteppedPiece {
    private static final PieceName PIECE_NAME = PieceName.ADVISOR;
    private static final Score PIECE_SCORE = new Score(3);

    public Advisor(Side side) {
        super(PIECE_NAME, side, PIECE_SCORE);
    }

    @Override
    public List<Movement> getMovements() {
        return Collections.emptyList();
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return candidatePath;
    }
}
