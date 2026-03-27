package domain.movestrategy;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public class GuardMoveStrategy implements MoveStrategy {

    private static final List<Delta> ALL_DIRECTIONS = List.of(
            Delta.UP, Delta.RIGHT_UP, Delta.RIGHT, Delta.RIGHT_DOWN,
            Delta.DOWN, Delta.LEFT_DOWN, Delta.LEFT, Delta.LEFT_UP
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return ALL_DIRECTIONS.stream()
                .map(from::move)
                .filter(position -> isNotAlly(position, pieces, from))
                .toList();
    }

    private boolean isNotAlly(Position next, Map<Position, Piece> pieces, Position from) {
        if (!pieces.containsKey(next)) {
            return true;
        }

        return pieces.get(from).getTeam() != pieces.get(next).getTeam();
    }
}
