package domain.piece.validation;

import domain.MoveInfos;
import domain.piece.category.PieceCategory;

public class CannonInIntermediatePathValidator implements MoveValidation {

    @Override
    public void validate(MoveInfos moveInfos) {
        if (moveInfos.hasSamePieceCategoryInPath(PieceCategory.CANNON)) {
            throw new IllegalArgumentException("포는 다른 포를 지나칠 수 없습니다.");
        }
    }
}
