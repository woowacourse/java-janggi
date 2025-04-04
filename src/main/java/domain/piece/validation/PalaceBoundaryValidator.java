package domain.piece.validation;

import domain.MoveInfos;

public class PalaceBoundaryValidator implements MoveValidation {

    @Override
    public void validate(final MoveInfos moveInfos) {
        if (!moveInfos.isLastPathWithinPalace()) {
            throw new IllegalArgumentException("궁성 밖으로 이동할 수 없습니다.");
        }
    }
}
