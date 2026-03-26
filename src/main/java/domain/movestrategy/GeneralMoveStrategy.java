package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

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

    // TODO: 궁성 내부만 이동 가능, 이동할 위치에 아군있으면 이동 불가, 궁끼리 직접 마주보기 불가
    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return OFFSET_POSITIONS.stream()
                .map(from::move)
                .toList();
    }
}
