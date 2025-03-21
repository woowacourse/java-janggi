package domain.piece;

import static domain.Offset.DOWN;
import static domain.Offset.UP;

import domain.BoardPosition;
import domain.Offset;
import domain.Piece;
import domain.PieceType;
import domain.Team;
import java.util.List;

public class Zzu extends Piece {

    public Zzu(final Team team) {
        super(PieceType.ZZU, team);
    }

    @Override
    public List<Offset> findMovementRule(
            final BoardPosition before,
            final BoardPosition after
    ) {
        final Offset offset = after.calculateOffset(before);
        validateNotMove(offset);
        validateOffset(offset);

        return List.of(offset);
    }

    @Override
    public boolean isObstacleCountAllowed(final int obstacleCount) {
        return obstacleCount == 0;
    }

    // TODO : Piece의 추상 메서드로 두어도 괜찮을 것 같다.
    private void validateOffset(final Offset offset) {
        if (!hasOneMovement(offset) || isMovingBackward(offset)) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean hasOneMovement(final Offset offset) {
        return (offset.x() == 0 && Math.abs(offset.y()) == 1) ||
                (Math.abs(offset.x()) == 1 && offset.y() == 0);
    }

    private boolean isMovingBackward(final Offset offset) {
        if (team == Team.RED) {
            return offset.equals(UP);
        }

        return offset.equals(DOWN);
    }
}
