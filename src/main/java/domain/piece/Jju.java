package domain.piece;

import domain.board.Movement;
import domain.board.Offset;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Jju extends Piece {

    // @formatter:off
    private static final Map<Team, Set<Offset>> PALACE_OFFSETS_BY_TEAM = Map.of(
        Team.GREEN, Set.of(Offset.RIGHT, Offset.LEFT, Offset.UP, Offset.RIGHT_UP, Offset.LEFT_UP),
        Team.RED, Set.of(Offset.RIGHT, Offset.LEFT, Offset.DOWN, Offset.RIGHT_DOWN, Offset.LEFT_DOWN)
    );

    private static final Map<Team, Set<Offset>> NORMAL_OFFSETS_BY_TEAM = Map.of(
        Team.GREEN, Set.of(Offset.RIGHT, Offset.LEFT, Offset.UP),
        Team.RED, Set.of(Offset.RIGHT, Offset.LEFT, Offset.DOWN)
    );
    // @formatter:on
    private static final Score SCORE = new Score(2.0f);

    public Jju(final Team team) {
        super(PieceType.JJU, team);
    }

    @Override
    public List<Offset> findMovementRule(final Movement movement) {
        final Offset offset = movement.calculateOffset();
        final Set<Offset> allowedOffsets = getAllowedOffsets(movement);

        if (!allowedOffsets.contains(offset)) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }

        return List.of(offset);
    }

    private Set<Offset> getAllowedOffsets(final Movement movement) {
        if (movement.isPalaceMovement()) {
            return PALACE_OFFSETS_BY_TEAM.get(team);
        }

        return NORMAL_OFFSETS_BY_TEAM.get(team);
    }

    @Override
    public Score getScore() {
        return SCORE;
    }
}
