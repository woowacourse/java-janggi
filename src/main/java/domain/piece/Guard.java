package domain.piece;

import domain.board.Offset;
import domain.board.movement.Movement;
import domain.janggi.Team;
import java.util.List;

public class Guard extends Piece {

    public Guard(final Team team) {
        super(PieceType.GUARD, team);
    }

    @Override
    protected List<Offset> createMovementRule(final Offset offset) {
        return List.of(offset);
    }

    @Override
    protected void validateMovement(final Movement movement) {
        if (!movement.isOneLineMovement() || !movement.isMoveInPalaceArea()) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }
}
