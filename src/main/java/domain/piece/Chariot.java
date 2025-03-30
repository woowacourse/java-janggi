package domain.piece;

import domain.board.Movement;
import domain.board.Offset;
import java.util.List;

public class Chariot extends Piece {

    private static final Score SCORE = new Score(13.0f);

    public Chariot(final Team team) {
        super(PieceType.CHARIOT, team);
    }

    @Override
    public List<Offset> findMovementRule(final Movement movement) {
        final Offset totalOffset = movement.calculateOffset();

        if (movement.isPalaceMovement()) {
            return totalOffset.calculateUnitSteps();
        }

        validateNonPalaceMovement(totalOffset);
        return totalOffset.calculateUnitSteps();
    }

    private void validateNonPalaceMovement(final Offset totalOffset) {
        if (!totalOffset.isLinear()) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }
    }

    @Override
    public Score getScore() {
        return SCORE;
    }
}
