package domain.piece;

import static domain.board.Offset.DOWN;
import static domain.board.Offset.UP;

import domain.board.BoardPosition;
import domain.board.Offset;
import domain.Team;
import java.util.List;

public class Zzu extends Piece {

    public Zzu(final Team team) {
        super(team);
    }

    @Override
    public List<Offset> findMovementRule(
            final BoardPosition before,
            final BoardPosition after
    ) {
        final Offset offset = after.calculateOffset(before);
        validateOffset(offset);

        return List.of(offset);
    }

    @Override
    public boolean isObstacleCountAllowed(final int obstacleCount) {
        return obstacleCount == 0;
    }

    private void validateOffset(final Offset offset) {
        if (!offset.hasOneMovement() || isMovingBackward(offset)) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean isMovingBackward(final Offset offset) {
        if (team == Team.RED) {
            return offset.equals(UP);
        }

        return offset.equals(DOWN);
    }

    @Override
    public String toString() {
        return "쭈";
    }
}
