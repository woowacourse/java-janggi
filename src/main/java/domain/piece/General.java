package domain.piece;

import domain.board.Movement;
import domain.board.Offset;
import java.util.List;

public class General extends Piece {

    public General(final Team team) {
        super(PieceType.GENERAL, team);
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
}
