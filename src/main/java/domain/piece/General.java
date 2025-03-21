package domain.piece;

import domain.board.BoardPosition;
import domain.board.Offset;
import domain.Team;
import java.util.List;

public class General extends Piece {

    public General(final Team team) {
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
    public boolean isAllowedObstacles(final List<Piece> obstacles) {
        return obstacles.isEmpty();
    }

    // TODO : 추후) 왕은 궁성 밖으로 나갈 수 없다.
    private void validateOffset(final Offset offset) {
        if (!offset.hasOneMovement()) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return "왕";
    }
}
