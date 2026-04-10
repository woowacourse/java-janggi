package janggi.domain.piece.linear;

import janggi.domain.board.BoardInfo;
import janggi.domain.path.CandidatePath;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Score;
import janggi.domain.side.Side;

public class Chariot extends LinearPiece {
    private static final PieceType PIECE_NAME = PieceType.CHARIOT;
    private static final Score PIECE_SCORE = new Score(13);

    public Chariot(Side side) {
        super(PIECE_NAME, side, PIECE_SCORE);
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, BoardInfo boardInfo) {
        return candidatePath.getPath().stream()
                .filter(point -> !boardInfo.isEmpty(point))
                .findFirst()
                .map(candidatePath::takeUntil)
                .orElse(candidatePath);
    }
}
