package domain.movestrategy;

import domain.piece.Position;
import java.util.List;

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

    @Override
    public List<Position> calculateMovablePositions(final Position from) {
        return OFFSET_POSITIONS.stream()
                .map(from::move)
                .toList();
    }
}
