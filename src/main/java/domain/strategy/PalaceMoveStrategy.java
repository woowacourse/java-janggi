package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PalaceMoveStrategy extends MoveStrategy {

    private static final PalaceMoveStrategy INSTANCE = new PalaceMoveStrategy();

    private PalaceMoveStrategy() {
    }

    public static PalaceMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        if (!isPalaceArea(destination)) {
            return false;
        }
        return createDestinations(currentPosition).contains(destination);
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        return true;
    }

    private boolean isPalaceArea(Position destination) {
        return palace.isPalaceRedArea(destination) || palace.isPalaceGreenArea(destination);
    }

    private List<Position> createDestinations(Position currentPosition) {
        List<Position> destinations = new ArrayList<>(basicPositions(currentPosition));
        destinations.addAll(palace.reachablePositionsInPalace(currentPosition));
        return destinations;
    }

    private List<Position> basicPositions(Position currentPosition) {
        return Stream.of(
                        currentPosition.right(),
                        currentPosition.down(),
                        currentPosition.up(),
                        currentPosition.left())
                .filter(this::isPalaceArea)
                .toList();
    }
}
