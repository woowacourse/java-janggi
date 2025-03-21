package domain.piece;

import domain.board.BoardPosition;
import domain.board.Offset;
import domain.Team;
import java.util.List;

public class General extends Piece {

    public General(final Team team) {
        super(PieceType.GENERAL, team);
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
    // TODO : 추후) 왕은 궁성 밖으로 나갈 수 없다.
    private void validateOffset(final Offset offset) {
        if (!offset.hasOneMovement()) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }
}
