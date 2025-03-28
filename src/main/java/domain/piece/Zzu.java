package domain.piece;

import domain.board.Offset;
import domain.board.movement.Movement;
import domain.janggi.Team;
import java.util.List;

public class Zzu extends Piece {

    public Zzu(final Team team) {
        super(PieceType.ZZU, team);
    }

    @Override
    protected List<Offset> createMovementRule(final Offset offset) {
        return List.of(offset);
    }

    @Override
    protected void validateMovement(final Movement movement) {
        final Offset offset = movement.calcaulteOffset();
        if (!movement.isOneLineMovement() || isMovingBackward(offset)) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean isMovingBackward(final Offset offset) {
        if (team == Team.RED) {
            return offset.isUpDirectionMove();
        }

        return offset.isDownDirectionMove();
    }
}
