package domain.strategy;

import domain.Position;
import java.util.List;

public class UpwardSoldierMoveStrategy extends MoveStrategy {

    private List<Position> destinations;

    private UpwardSoldierMoveStrategy(Position position) {
        super(position);
        this.destinations = createDestinations();
    }

    public static UpwardSoldierMoveStrategy of(Position position) {
        return new UpwardSoldierMoveStrategy(position);
    }

    @Override
    public void updateRoute() {
        this.destinations = createDestinations();
    }

    private List<Position> createDestinations() {
        return List.of(
                position().right(),
                position().down(),
                position().left()
        );
    }

    @Override
    public boolean canMoveTo(Position destination) {
        return destinations.contains(destination);
    }

    @Override
    public boolean hasPieceInPath(Position targetPosition, List<Position> occupiedPositions) {
        return false;
    }
}
