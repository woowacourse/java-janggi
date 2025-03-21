package domain.piece;

import static domain.Offset.DOWN;
import static domain.Offset.LEFT;
import static domain.Offset.LEFT_DOWN;
import static domain.Offset.LEFT_UP;
import static domain.Offset.RIGHT;
import static domain.Offset.RIGHT_DOWN;
import static domain.Offset.RIGHT_UP;
import static domain.Offset.UP;

import domain.BoardPosition;
import domain.Offset;
import domain.Piece;
import domain.PieceType;
import domain.Team;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    private static final Map<Offset, List<Offset>> MOVEMENT_RULES = Map.of(
            new Offset(2, 1), List.of(RIGHT, RIGHT_UP),
            new Offset(2, -1), List.of(RIGHT, RIGHT_DOWN),
            new Offset(-2, 1), List.of(LEFT, LEFT_UP),
            new Offset(-2, -1), List.of(LEFT, LEFT_DOWN),
            new Offset(1, 2), List.of(UP, RIGHT_UP),
            new Offset(1, -2), List.of(DOWN, RIGHT_DOWN),
            new Offset(-1, 2), List.of(UP, LEFT_UP),
            new Offset(-1, -2), List.of(DOWN, LEFT_DOWN)
    );

    public Horse(final Team team) {
        super(PieceType.HORSE, team);
    }

    @Override
    public List<Offset> findMovementRule(
            final BoardPosition before,
            final BoardPosition after
    ) {
        final Offset offset = after.calculateOffset(before);
        validateNotMove(offset);
        validateOffset(offset);

        return MOVEMENT_RULES.get(offset);
    }

    @Override
    public boolean isObstacleCountAllowed(final int obstacleCount) {
        return obstacleCount == 0;
    }

    // TODO : Piece의 추상 메서드로 두어도 괜찮을 것 같다.
    private void validateOffset(final Offset offset) {
        if (!MOVEMENT_RULES.containsKey(offset)) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }
}
