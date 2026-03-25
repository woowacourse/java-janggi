package domain.strategy;

import domain.Position;
import java.util.List;

public class HorseMoveStrategy extends MoveStrategy {

    private static final int[] DR = {1, 1, -1, -1, 2, 2, -2, -2};
    private static final int[] DC = {2, -2, 2, -2, 1, -1, 1, -1};

    private final List<Position> destinations;

    HorseMoveStrategy(Position position, List<Position> destinations) {
        super(position);
        this.destinations = destinations;
    }

    @Override
    public boolean isMoveAble(Position position) {
        return false;
    }

    @Override
    public boolean isRouteBlockedBy(Position destination, List<Position> piecePositions) {
        return false;
    }
}
