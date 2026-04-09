package janggi.domain.piece.stepped;

import janggi.domain.board.BoardInfo;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.generator.FixedPathStrategy;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.side.Side;

public abstract class SteppedPiece extends Piece {
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    protected SteppedPiece(PieceName name, Side side, Score score) {
        super(name, side, DEFAULT_STRATEGY, score);
    }

    @Override
    protected boolean isValidPath(CandidatePath candidatePath, BoardInfo boardInfo) {
        if (candidatePath.isEmpty()) {
            return false;
        }
        return candidatePath.isThereNoPieceInPathExceptForLast(boardInfo);
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, BoardInfo boardInfo) {
        return candidatePath.takeLast();
    }
}
