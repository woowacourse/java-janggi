package domain.piece.policy;

import domain.BoardStatus;
import domain.position.Position;
import java.util.List;

public class NormalMovementPolicy implements MovementPolicy {
    @Override
    public boolean isMovable(BoardStatus boardStatus, List<Position> movablePath) {
        for (Position path : movablePath) {
            boardStatus.isPieceAt(path);
        }
        return true;
    }
}
