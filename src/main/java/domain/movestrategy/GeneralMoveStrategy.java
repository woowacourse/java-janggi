package domain.movestrategy;

import domain.piece.Position;
import java.util.List;

public class GeneralMoveStrategy implements MoveStrategy {

    private static final List<Position> OFFSET_POSITIONS = List.of(
            Position.of(-1, -1),
            Position.of(-1, 0),
            Position.of(-1, 1),
            Position.of(0, 1),
            Position.of(1, 1),
            Position.of(1, 0),
            Position.of(1, -1),
            Position.of(0, -1)
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from) {
        return OFFSET_POSITIONS.stream()
                .map(from::move)
                .toList();
    }
}
