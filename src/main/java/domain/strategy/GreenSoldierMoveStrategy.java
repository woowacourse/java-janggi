package domain.strategy;

import domain.MoveRoute;
import domain.Position;
import domain.moverule.GreenSoldierMoveRule;
import java.util.List;

public class GreenSoldierMoveStrategy extends MoveStrategy {

    private final List<Position> destinations;

    private GreenSoldierMoveStrategy(Position position) {
        super(position);
        this.destinations = setupDestinations();
    }

    public static GreenSoldierMoveStrategy of(Position position) {
        return new GreenSoldierMoveStrategy(position);
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
        return GreenSoldierMoveRule.moveRoutesOf(position).stream()
                .map(MoveRoute::destination)
                .toList();
    }
}
