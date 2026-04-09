package janggi.domain.piece.linear;

import janggi.domain.path.CandidatePath;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.Score;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;

public class Chariot extends LinearPiece {
    private static final PieceName PIECE_NAME = PieceName.CHARIOT;
    private static final Score PIECE_SCORE = new Score(13);

    public Chariot(Side side) {
        super(PIECE_NAME, side, PIECE_SCORE);
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return candidatePath.getPath().stream()
                .filter(piecesOnPaths::containsKey)
                .findFirst()
                .map(candidatePath::takeUntil)
                .orElse(candidatePath);
    }
}
