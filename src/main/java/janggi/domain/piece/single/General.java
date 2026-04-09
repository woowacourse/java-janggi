package janggi.domain.piece.single;

import janggi.domain.board.BoardInfo;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.Movement;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;

public class General extends SinglePiece {
    private static final PieceName PIECE_NAME = PieceName.GENERAL;
    private static final Score PIECE_SCORE = Score.NONE;

    public General(Side side) {
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
