package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;

public class GeneralMoveStrategy extends MoveStrategy {

    private List<Position> destinations;

    private GeneralMoveStrategy(Position position) {
        super(position);
        this.destinations = createDestinations();
    }

    @Override
    public void updateRoute() {
        this.destinations = createDestinations();
    }

    public static GeneralMoveStrategy of(Position position) {
        return new GeneralMoveStrategy(position);
    }

    private List<Position> createDestinations() {
        return List.of(
                position().right(),
                position().down(),
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
