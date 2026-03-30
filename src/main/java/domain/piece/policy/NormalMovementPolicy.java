package domain.piece.policy;

import domain.PieceExceptionMessage;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class NormalMovementPolicy implements MovementPolicy {
    @Override
    public void check(Map<Position, Piece> positionInfoOfBoard, List<Position> movablePath) {
        for (Position position : movablePath) {
            if (positionInfoOfBoard.get(position) != null) {
                throw new IllegalArgumentException(PieceExceptionMessage.BLOCKED_BY_PIECE.getMessage());
            }
        }
    }
}
