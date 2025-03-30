package domain.piece;

import domain.board.Movement;
import domain.board.Offset;
import java.util.List;

public class Guard extends Piece {

    private static final Score SCORE = new Score(3.0f);

    public Guard(final Team team) {
        super(PieceType.GUARD, team);
    }

    @Override
    public List<Offset> findMovementRule(
        final Movement movement
    ) {
        if (!movement.isPalaceMovement()) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }

        final Offset totalOffset = movement.calculateOffset();

        if (!totalOffset.isSingleStep()) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }

        return List.of(totalOffset);
    }

    @Override
    public Score getScore() {
        return SCORE;
    }
}
