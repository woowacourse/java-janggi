package domain.board.movement;

import domain.board.BoardPosition;
import domain.board.Offset;
import java.util.Set;

public class PalaceMovement extends Movement {

    protected static final Set<BoardPosition> PALACE_MOVEMENT_POSITIONS = Set.of(
            new BoardPosition(3, 0), new BoardPosition(5, 0), new BoardPosition(4, 1),
            new BoardPosition(3, 2), new BoardPosition(5, 2),
            new BoardPosition(3, 9), new BoardPosition(5, 9), new BoardPosition(4, 8),
            new BoardPosition(3, 7), new BoardPosition(5, 7)
    );

    public PalaceMovement(
            final BoardPosition before,
            final BoardPosition after
    ) {
        super(before, after);
        validateIsPalaceMovement(before, after);
    }

    @Override
    public boolean isMoveOnLine() {
        final Offset offset = after.calculateOffset(before);
        return offset.isStraightMove() || offset.isDiagonalMove();
    }

    @Override
    public boolean isOneLineMovement() {
        final Offset offset = after.calculateOffset(before);
        return offset.hasOneStraightMove() || offset.hasOneDiagonalMove();
    }

    private void validateIsPalaceMovement(
            final BoardPosition before,
            final BoardPosition after
    ) {
        if (!PalaceMovement.PALACE_MOVEMENT_POSITIONS.contains(before) ||
                !PalaceMovement.PALACE_MOVEMENT_POSITIONS.contains(after)) {
            throw new IllegalCallerException("해당 이동은 궁성 이동이 아닙니다.");
        }
    }
}
