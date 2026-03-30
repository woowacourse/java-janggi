package domain.piece.policy;

import domain.BoardStatus;
import domain.position.Position;
import java.util.List;

public interface MovementPolicy {
    boolean isMovable(BoardStatus boardStatus, List<Position> movablePath);
}
