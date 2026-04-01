package domain.strategy;

import domain.Position;
import java.util.List;

public class NonMoveableStrategy extends MoveStrategy {

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        return false;
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        return false;
    }
}
