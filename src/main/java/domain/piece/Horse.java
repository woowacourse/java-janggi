package domain.piece;

import domain.board.BoardPosition;
import domain.board.Offset;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    private static final Map<Offset, List<Offset>> MOVEMENT_RULES = Map.of(
        new Offset(2, 1), List.of(new Offset(1, 0), new Offset(1, 1)),
        new Offset(2, -1), List.of(new Offset(1, 0), new Offset(1, -1)),
        new Offset(-2, 1), List.of(new Offset(-1, 0), new Offset(-1, 1)),
        new Offset(-2, -1), List.of(new Offset(-1, 0), new Offset(-1, -1)),
        new Offset(1, 2), List.of(new Offset(0, 1), new Offset(1, 1)),
        new Offset(1, -2), List.of(new Offset(0, -1), new Offset(1, -1)),
        new Offset(-1, 2), List.of(new Offset(0, 1), new Offset(-1, 1)),
        new Offset(-1, -2), List.of(new Offset(0, -1), new Offset(-1, -1))
    );

    public Horse(final Team team) {
        super(PieceType.HORSE, team);
    }

    @Override
    public List<Offset> findMovementRule(
        final BoardPosition selectedPosition,
        final BoardPosition destinationBoardPosition
    ) {
        final Offset totalOffset = destinationBoardPosition.calculateOffset(selectedPosition);

        if (!MOVEMENT_RULES.containsKey(totalOffset)) {
            throw new IllegalArgumentException("해당 말은 이동할 수 없습니다.");
        }

        return MOVEMENT_RULES.get(totalOffset);
    }
}
