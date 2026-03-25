package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public class GuardMoveStrategy implements MoveStrategy {

    // TODO: 상하좌우 이동하는 MoveStrategy 만들어서 재사용
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
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return OFFSET_POSITIONS.stream()
                .map(from::move)
                .toList();
    }
}
