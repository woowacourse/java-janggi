package janggi.domain.piece.single;

import janggi.domain.board.BoardInfo;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.Movement;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;

public class Advisor extends SinglePiece {
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
    protected boolean isValidPath(CandidatePath candidatePath, BoardInfo boardInfo) {
        return !candidatePath.isEmpty();
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, BoardInfo boardInfo) {
        return candidatePath;
    }
}
