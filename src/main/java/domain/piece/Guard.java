package domain.piece;

import domain.board.BoardPosition;
import domain.board.Offset;
import domain.Team;
import java.util.List;

public class Guard extends Piece {

    public Guard(final Team team) {
        super(PieceType.GUARD, team);
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
    // TODO : 추후) 사는 궁성 밖으로 나갈 수 없다.
    private void validateOffset(final Offset offset) {
        if (offset.x() == 0 && Math.abs(offset.y()) == 1) {
            return;
        }
        if (Math.abs(offset.x()) == 1 && offset.y() == 0) {
            return;
        }
        throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
    }
}
