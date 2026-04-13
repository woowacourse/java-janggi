package janggi.domain.piece.single;

import janggi.domain.board.BoardInfo;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Score;
import janggi.domain.piece.path.CandidatePath;
import janggi.domain.piece.path.Movement;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;

public class Advisor extends SinglePiece {
    private static final PieceType PIECE_NAME = PieceType.ADVISOR;
    private static final Score PIECE_SCORE = new Score(3);

    public Advisor(Side side) {
        super(PIECE_NAME, side, PIECE_SCORE);
    }

    @Override
    protected List<Movement> getMovements() {
        return Collections.emptyList();
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, BoardInfo boardInfo) {
        return candidatePath;
    }
}
