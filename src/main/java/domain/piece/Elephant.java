package domain.piece;

import domain.board.Movement;
import domain.board.Offset;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    private static final Map<Offset, List<Offset>> MOVEMENT_RULES = Map.of(
        new Offset(3, 2), List.of(Offset.RIGHT, Offset.RIGHT_UP, Offset.RIGHT_UP),
        new Offset(3, -2), List.of(Offset.RIGHT, Offset.RIGHT_DOWN, Offset.RIGHT_DOWN),
        new Offset(-3, 2), List.of(Offset.LEFT, Offset.LEFT_UP, Offset.LEFT_UP),
        new Offset(-3, -2), List.of(Offset.LEFT, Offset.LEFT_DOWN, Offset.LEFT_DOWN),
        new Offset(2, 3), List.of(Offset.UP, Offset.RIGHT_UP, Offset.RIGHT_UP),
        new Offset(2, -3), List.of(Offset.DOWN, Offset.RIGHT_DOWN, Offset.RIGHT_DOWN),
        new Offset(-2, 3), List.of(Offset.UP, Offset.LEFT_UP, Offset.LEFT_UP),
        new Offset(-2, -3), List.of(Offset.DOWN, Offset.LEFT_DOWN, Offset.LEFT_DOWN)
    );
    private static final Score SCORE = new Score(3.0f);

    public Elephant(final Team team) {
        super(PieceType.ELEPHANT, team);
    }

    @Override
    public List<Offset> findMovementRule(final Movement movement) {
        final Offset totalOffset = movement.calculateOffset();

        if (!MOVEMENT_RULES.containsKey(totalOffset)) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }

        return MOVEMENT_RULES.get(totalOffset);
    }

    @Override
    public Score getScore() {
        return SCORE;
    }
}
