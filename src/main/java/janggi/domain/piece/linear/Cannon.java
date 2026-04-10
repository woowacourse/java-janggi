package janggi.domain.piece.linear;

import janggi.domain.board.BoardInfo;
import janggi.domain.path.CandidatePath;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Score;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Collections;

public class Cannon extends LinearPiece {
    private static final PieceType PIECE_NAME = PieceType.CANNON;
    private static final Score PIECE_SCORE = new Score(7);

    public Cannon(Side side) {
        super(PIECE_NAME, side, PIECE_SCORE);
    }

    @Override
    protected CandidatePath refinePath(CandidatePath candidatePath, BoardInfo boardInfo) {
        Point firstEncountedPoint = candidatePath.getPointEncounterPiece(boardInfo, 1);
        Point secondEncountedPoint = candidatePath.getPointEncounterPiece(boardInfo, 2);

        if (firstEncountedPoint != null && boardInfo.isSameType(firstEncountedPoint, this)) {
            return new CandidatePath(Collections.emptyList());
        }
        CandidatePath refinedPath = candidatePath.between(firstEncountedPoint, secondEncountedPoint);
        if (secondEncountedPoint != null && !boardInfo.isSameType(secondEncountedPoint, this)) {
            return refinedPath.add(secondEncountedPoint);
        }
        return refinedPath;
    }

}
