package domain.piece.validation;

import domain.MoveInfos;

public class CannonIntermediatePieceCountValidator implements MoveValidation {

    private static final int PIECES_TO_PASS = 1;

    @Override
    public void validate(MoveInfos moveInfos) {
        if (moveInfos.countPiecesInIntermediatePath() != PIECES_TO_PASS) {
            throw new IllegalArgumentException("중간에 기물이 " + PIECES_TO_PASS + "개여야 합니다.");
        }
    }
}
