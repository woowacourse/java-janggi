package domain.strategy;

import domain.Palace;
import domain.Position;
import java.util.List;

public abstract class MoveStrategy {

    protected final Palace palace = new Palace();

    public abstract boolean canMoveTo(Position currentPosition, Position destination);

    public abstract boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions);
}
