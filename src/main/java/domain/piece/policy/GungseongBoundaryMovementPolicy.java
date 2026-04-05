package domain.piece.policy;

import domain.Board;
import domain.PieceExceptionMessage;
import domain.position.Position;
import java.util.List;

public class GungseongBoundaryMovementPolicy implements MovementPolicy {
    private static final List<Position> gungseongPositions = List.of(
            Position.of(1, 4), Position.of(1, 5), Position.of(1, 6),
            Position.of(2, 4), Position.of(2, 5), Position.of(2, 6),
            Position.of(3, 4), Position.of(3, 5), Position.of(3, 6),
            Position.of(8, 4), Position.of(8, 5), Position.of(8, 6),
            Position.of(9, 4), Position.of(9, 5), Position.of(9, 6),
            Position.of(10, 4), Position.of(10, 5), Position.of(10, 6));

    @Override
    public void validate(Board board, List<Position> path, Position start, Position destination) {
        for (Position position : gungseongPositions) {
            if (position.equals(destination)) {
                return;
            }
        }
        throw new IllegalArgumentException(PieceExceptionMessage.CAN_NOT_GO_OUT_OF_GUNGSEONG.getMessage());
    }
}
