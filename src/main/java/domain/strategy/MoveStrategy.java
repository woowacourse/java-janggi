package domain.strategy;

import domain.Position;
import java.util.List;

public abstract class MoveStrategy {

    private Position position;

    protected MoveStrategy(Position position) {
        this.position = position;
    }

    public abstract void updateRoute();

    public abstract boolean canMoveTo(Position destination);

    public abstract boolean hasValidPathTo(Position destination, List<Position> occupiedPositions);

    public void moveTo(Position destination) {
        this.position = destination;
    }

    public Position position() {
        return position;
    }
}
