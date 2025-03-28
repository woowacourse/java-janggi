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

public class Elephant extends Piece {

    private static final Map<Offset, List<Offset>> MOVEMENT_RULES = Map.of(
            new Offset(3, 2), List.of(RIGHT, RIGHT_UP, RIGHT_UP),
            new Offset(3, -2), List.of(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
            new Offset(-3, 2), List.of(LEFT, LEFT_UP, LEFT_UP),
            new Offset(-3, -2), List.of(LEFT, LEFT_DOWN, LEFT_DOWN),
            new Offset(2, 3), List.of(UP, RIGHT_UP, RIGHT_UP),
            new Offset(2, -3), List.of(DOWN, RIGHT_DOWN, RIGHT_DOWN),
            new Offset(-2, 3), List.of(UP, LEFT_UP, LEFT_UP),
            new Offset(-2, -3), List.of(DOWN, LEFT_DOWN, LEFT_DOWN)
    );

    public Elephant(final Team team) {
        super(PieceType.ELEPHANT, team);
    }

    @Override
    protected List<Offset> createMovementRule(final Offset offset) {
        return MOVEMENT_RULES.get(offset);
    }

    @Override
    protected void validateMovement(final Movement movement) {
        final Offset offset = movement.calcaulteOffset();
        if (!MOVEMENT_RULES.containsKey(offset)) {
            throw new IllegalArgumentException("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }
}
