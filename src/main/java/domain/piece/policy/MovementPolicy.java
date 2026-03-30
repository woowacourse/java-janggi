package domain.piece.policy;

import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public interface MovementPolicy {
    void check(Map<Position, Piece> positionInfoOfBoard, List<Position> movablePath);
}
