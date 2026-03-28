package domain.strategy;

import domain.moverule.GeneralMoveRule;
import domain.Position;
import java.util.Arrays;
import java.util.List;

public class GeneralMoveStrategy extends MoveStrategy {

    private final List<Position> destinations;

    private GeneralMoveStrategy(Position position) {
        super(position);
        this.destinations = setupDestinations();
    }

    public static GeneralMoveStrategy of(Position position) {
        return new GeneralMoveStrategy(position);
    }

    @Override
    public boolean isMoveAble(Position targetPosition) {
        return destinations.contains(targetPosition);
    }

    @Override
    public boolean isPathRestricted(Position destination, List<Position> piecePositions) {
        return false;
    }

    private List<Position> setupDestinations() {
        return Arrays.stream(GeneralMoveRule.values())
                .map(generalMoveRule -> generalMoveRule.destination(position))
                .toList();
    }
}
