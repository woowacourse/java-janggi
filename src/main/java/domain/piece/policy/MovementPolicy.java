package domain.piece.policy;

import domain.Board;
import domain.position.Position;
import java.util.List;

public interface MovementPolicy {
    void validate(Board board, List<Position> path, Position start, Position destination);
}
