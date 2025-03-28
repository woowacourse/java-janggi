package domain.piece;

import static domain.board.Offset.DOWN;
import static domain.board.Offset.LEFT;
import static domain.board.Offset.LEFT_DOWN;
import static domain.board.Offset.LEFT_UP;
import static domain.board.Offset.RIGHT;
import static domain.board.Offset.RIGHT_DOWN;
import static domain.board.Offset.RIGHT_UP;
import static domain.board.Offset.UP;

import domain.board.Offset;
import domain.board.movement.Movement;
import domain.janggi.Team;
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
    protected void validateMovement(final Movement movement) {
        final Offset offset = movement.calcaulteOffset();
        if (!MOVEMENT_RULES.containsKey(offset)) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Offset> createMovementRule(final Offset offset) {
        return MOVEMENT_RULES.get(offset);
    }
}
