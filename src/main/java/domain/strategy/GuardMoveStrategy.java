package domain.strategy;

import domain.GuardMoveRule;
import domain.Position;
import java.util.Arrays;
import java.util.List;

public class GuardMoveStrategy extends MoveStrategy {

    private final List<Position> destinations;

    private GuardMoveStrategy(Position position) {
        super(position);
        this.destinations = setupDestinations();
    }

    public static GuardMoveStrategy of(Position position) {
        return new GuardMoveStrategy(position);
    }

    @Override
    public boolean isMoveAble(Position targetPosition) {
        return destinations.contains(targetPosition);
    }

    @Override
    public boolean hasPieceOnPath(Position destination, List<Position> piecePositions) {
        return false;
    }

    private List<Position> setupDestinations() {
        return Arrays.stream(GuardMoveRule.values())
                .map(guardMoveRule -> guardMoveRule.destination(position))
                .toList();
    }
}
