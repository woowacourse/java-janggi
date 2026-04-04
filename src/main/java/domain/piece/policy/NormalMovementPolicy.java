package domain.piece.policy;

import domain.Board;
import domain.PieceExceptionMessage;
import domain.position.Position;
import java.util.List;

public class NormalMovementPolicy implements MovementPolicy {
    @Override
    public void validate(Board board, List<Position> path, Position start, Position destination) {
        // 마지막 칸을 제외한 경로에 기물이 있는지 확인한다.
        for (int index = 0; index < path.size() - 1; index++) {
            Position current = path.get(index);
            if (board.getPieceWithNull(current) != null) {
                throw new IllegalArgumentException(PieceExceptionMessage.BLOCKED_BY_PIECE.getMessage());
            }
        }
    }
}
