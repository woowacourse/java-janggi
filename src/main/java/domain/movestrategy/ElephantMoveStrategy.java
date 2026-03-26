package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

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

    // TODO: 경로에 장애물 있으면 이동 불가, 이동할 위치에 아군 있으면 이동 불가
    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return OFFSET_POSITIONS.stream()
                .map(from::move)
                .toList();
    }
}
