package domain.strategy;

import domain.Position;
import domain.moverule.RedSoldierMoveRule;
import java.util.Arrays;
import java.util.List;

public class RedSoldierMoveStrategy extends MoveStrategy {

    private final List<Position> destinations;

    private RedSoldierMoveStrategy(Position position) {
        super(position);
        this.destinations = setupDestinations();
    }

    public static RedSoldierMoveStrategy of(Position position) {
        return new RedSoldierMoveStrategy(position);
    }

    @Override
    public boolean isMoveAble(Position targetPosition) {
        return destinations.contains(targetPosition);
    }

    @Override
    public boolean isPathRestricted(Position targetPosition, List<Position> piecePositions) {
        return false;
    }

    private List<Position> setupDestinations() {
        return Arrays.stream(RedSoldierMoveRule.values())
                .map(redSoldierMoveRule -> redSoldierMoveRule.destination(position))
                .toList();
    }
}
