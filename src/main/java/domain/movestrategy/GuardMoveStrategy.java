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

    // TODO: 궁성 내부만 이동 가능 + 이동할 위치에 아군이 있으면 이동 불가
    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return OFFSET_POSITIONS.stream()
                .map(from::move)
                .toList();
    }
}
