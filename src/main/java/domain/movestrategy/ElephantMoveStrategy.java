package domain.movestrategy;

import domain.piece.Position;
import java.util.List;

public class ElephantMoveStrategy implements MoveStrategy {

    private static final List<Position> OFFSET_POSITIONS = List.of(
            Position.of(-3, -2),
            Position.of(-3, 2),
            Position.of(-2, 3),
            Position.of(2, 3),
            Position.of(3, -2),
            Position.of(3, 2),
            Position.of(-2, -3),
            Position.of(2, -3)
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from) {
        return OFFSET_POSITIONS.stream()
                .map(from::move)
                .toList();
    }
}
