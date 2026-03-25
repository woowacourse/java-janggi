package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    private static final List<Position> OFFSET_POSITIONS = List.of(
            Position.of(-2, -1),
            Position.of(-2, 1),
            Position.of(-1, 2),
            Position.of(1, 2),
            Position.of(2, -1),
            Position.of(2, 1),
            Position.of(-1, -2),
            Position.of(1, -2)
    );

    private static final List<Position> CHECK_OFFSET = List.of(
            Position.of(1, 0),
            Position.of(-1, 0),
            Position.of(0, 1),
            Position.of(0, -1)
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return OFFSET_POSITIONS.stream()
                .map(from::move)
                .toList();
    }
}
