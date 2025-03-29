package domain.piece;

import domain.board.BoardPosition;
import domain.board.Offset;
import java.util.List;
import java.util.Map;

public class Jju extends Piece {

    private static final Map<Team, Map<Offset, List<Offset>>> MOVEMENT_RULES_BY_TEAM = Map.of(
        Team.GREEN, Map.of(
            new Offset(1, 0), List.of(new Offset(1, 0)),
            new Offset(-1, 0), List.of(new Offset(-1, 0)),
            new Offset(0, 1), List.of(new Offset(0, 1))
        ),
        Team.RED, Map.of(
            new Offset(1, 0), List.of(new Offset(1, 0)),
            new Offset(-1, 0), List.of(new Offset(-1, 0)),
            new Offset(0, -1), List.of(new Offset(0, -1))
        )
    );

    public Jju(final Team team) {
        super(PieceType.JJU, team);
    }

    @Override
    public List<Offset> findMovementRule(
        final BoardPosition selectedPosition,
        final BoardPosition destinationBoardPosition
    ) {
        final Offset totalOffset = destinationBoardPosition.calculateOffset(selectedPosition);
        final Map<Offset, List<Offset>> movementRules = MOVEMENT_RULES_BY_TEAM.get(super.team);

        if (!movementRules.containsKey(totalOffset)) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }

        return movementRules.get(totalOffset);
    }

}
