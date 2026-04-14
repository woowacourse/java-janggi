package domain.strategy;

import domain.Position;
import java.util.List;

public class CannonMoveStrategy extends OrthogonalMoveStrategy {

    private CannonMoveStrategy(Position position) {
        super(position);
    }

    public static CannonMoveStrategy of(Position position) {
        return new CannonMoveStrategy(position);
    }

    @Override
    public boolean isPathRestricted(Position destination, List<Position> piecePositions) {
        List<Position> route = routePositions(destination);
        return piecePositions.stream().filter(route::contains).count() != 1;
    }
}
