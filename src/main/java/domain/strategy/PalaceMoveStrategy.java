package domain.strategy;

import domain.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PalaceMoveStrategy extends MoveStrategy {

    private static final String ERROR_GENERAL_CAN_MOVE_ONLY_WITHIN_PALACE = "[ERROR] 해당 기물은 궁성 내부에서만 움직일 수 있다.";

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        validatePalaceArea(destination);
        return createDestinations(currentPosition).contains(destination);
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        return true;
    }

    private void validatePalaceArea(Position destination) {
        if (!isPalaceArea(destination)) {
            throw new IllegalArgumentException(ERROR_GENERAL_CAN_MOVE_ONLY_WITHIN_PALACE);
        }
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
