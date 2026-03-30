package domain.strategy;

import domain.Position;
import java.util.List;

public class NonMoveableStrategy extends MoveStrategy {

    private NonMoveableStrategy(Position position) {
        super(position);
    }

    public static NonMoveableStrategy of(Position position) {
        return new NonMoveableStrategy(position);
    }

    @Override
    public void updateRoute() {
    }

    @Override
    public boolean canMoveTo(Position destination) {
        return false;
    }

    @Override
    public boolean hasValidPathTo(Position destination, List<Position> occupiedPositions) {
        return false;
    }
}
