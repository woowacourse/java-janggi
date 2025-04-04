package domain.piece.validation;

import domain.MoveInfos;

public class DiagonalPalacePathValidator implements MoveValidation {

    @Override
    public void validate(final MoveInfos moveInfos) {
        if (moveInfos.isDiagonalPath()) {
            validateLastPathWithinPalace(moveInfos);
        }
    }

    private void validateLastPathWithinPalace(final MoveInfos moveInfos) {
        if (!moveInfos.isLastPathWithinPalace()) {
            throw new IllegalArgumentException("궁성 이동의 경우 밖으로 이동할 수 없습니다.");
        }
    }
}
