package domain.strategy;

import domain.Position;
import java.util.List;

public class GeneralMoveStrategy extends MoveStrategy {

    private List<Position> createDestinations(Position currentPosition) {
        return List.of(
                currentPosition.right(),
                currentPosition.down(),
                currentPosition.up(),
                currentPosition.left()
        );
    }

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        return createDestinations(currentPosition).contains(destination);
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        return true;
    }
}
