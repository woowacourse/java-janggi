package janggi.domain.piece.single;

import janggi.domain.board.BoardInfo;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.Direction;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.side.Side;

public class Soldier extends SinglePiece {
    private static final PieceName PIECE_NAME = PieceName.SOLDIER;
    private static final Score PIECE_SCORE = new Score(2);

    public Soldier(Side side) {
        super(PIECE_NAME, side, PIECE_SCORE);
    }

    @Override
    protected boolean isValidPath(CandidatePath candidatePath, BoardInfo boardInfo) {
        if (candidatePath.isEmpty()) {
            return false;
        }
        if (side.equals(Side.HAN) && candidatePath.isForward(Direction.NORTH)) {
            return false;
        }
        return !side.equals(Side.CHO) || !candidatePath.isForward(Direction.SOUTH);
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, BoardInfo boardInfo) {
        return candidatePath;
    }
}
