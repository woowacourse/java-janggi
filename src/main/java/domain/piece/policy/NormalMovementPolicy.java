package domain.piece.policy;

import domain.Board;
import domain.PieceExceptionMessage;
import domain.position.Position;
import java.util.List;

public class NormalMovementPolicy implements MovementPolicy {
    @Override
    public void validate(Board board, List<Position> path, Position start, Position destination) {
        if (board.hasAnyPieceInPath(path.subList(0, path.size() - 1))) {
            throw new IllegalArgumentException(PieceExceptionMessage.BLOCKED_BY_PIECE.getMessage());
        }
    }
}
