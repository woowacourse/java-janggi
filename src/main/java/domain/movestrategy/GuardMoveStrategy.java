package domain.movestrategy;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public class GuardMoveStrategy implements MoveStrategy {

    private static final List<Delta> ALL_DIRECTIONS = List.of(
            Delta.up(), Delta.rightUp(), Delta.right(), Delta.rightDown(),
            Delta.down(), Delta.leftDown(), Delta.left(), Delta.leftUp()
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Map<Position, Piece> pieces) {
        return ALL_DIRECTIONS.stream()
                .map(from::move)
                .toList();
    }
}
