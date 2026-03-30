package domain.piece.strategy;

import domain.position.Position;
import java.util.List;

public class EmptyMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        throw new UnsupportedOperationException(
                MoveStrategyErrorMessage.EMPTY_PIECE.getMessage()
        );
    }
}
