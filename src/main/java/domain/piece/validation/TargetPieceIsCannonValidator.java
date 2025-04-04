package domain.piece.validation;

import domain.MoveInfos;
import domain.piece.category.PieceCategory;

public class TargetPieceIsCannonValidator implements MoveValidation {

    @Override
    public void validate(MoveInfos moveInfos) {
        if (moveInfos.isSameAsTargetPiece(PieceCategory.CANNON)) {
            throw new IllegalArgumentException("포는 상대 포를 잡을 수 없습니다.");
        }
    }
}
