package domain.board.movement;

import domain.board.BoardPosition;
import domain.board.Offset;

public class NormalMovement extends Movement {

    public NormalMovement(
            final BoardPosition before,
            final BoardPosition after
    ) {
        super(before, after);
        validateIsNotPlaceMovement(before, after);
    }

    @Override
    public boolean isMoveOnLine() {
        final Offset offset = after.calculateOffset(before);
        return offset.isStraightMove();
    }

    @Override
    public boolean isOneLineMovement() {
        final Offset offset = after.calculateOffset(before);
        return offset.hasOneStraightMove();
    }

    private void validateIsNotPlaceMovement(
            final BoardPosition before,
            final BoardPosition after
    ) {
        if (PalaceMovement.PALACE_MOVEMENT_POSITIONS.contains(before) &&
                PalaceMovement.PALACE_MOVEMENT_POSITIONS.contains(after)) {
            throw new IllegalCallerException("해당 이동은 일반 이동이 아닙니다.");
        }
    }
}
