package domain.strategy;

import domain.Position;
import java.util.List;

public class DownwardSoldierMoveStrategy extends MoveStrategy {

    private List<Position> destinations;

    private DownwardSoldierMoveStrategy(Position position) {
        super(position);
        this.destinations = createDestinations();
    }

    public static DownwardSoldierMoveStrategy of(Position position) {
        return new DownwardSoldierMoveStrategy(position);
    }

    @Override
    public void updateRoute() {
        this.destinations = createDestinations();
    }

    private List<Position> createDestinations() {
        return List.of(
                position().right(),
                position().up(),
                position().left()
        );
    }

    @Override
    public boolean canMoveTo(Position destination) {
        return destinations.contains(destination);
    }

    @Override
    public boolean hasValidPathTo(Position destination, List<Position> occupiedPositions) {
        return true;
    }
}
