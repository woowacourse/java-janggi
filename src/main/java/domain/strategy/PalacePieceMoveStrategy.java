package domain.strategy;

import domain.MoveRoute;
import domain.Position;
import domain.moverule.PalaceMoveRule;
import java.util.List;

public class PalacePieceMoveStrategy extends MoveStrategy {

    private final List<Position> destinations;

    private PalacePieceMoveStrategy(Position position) {
        super(position);
        this.destinations = setupDestinations();
    }

    public static PalacePieceMoveStrategy of(Position position) {
        return new PalacePieceMoveStrategy(position);
    }

    @Override
    public boolean isMoveAble(Position targetPosition) {
        if (!targetPosition.isInPalace()) {
            return false;
        }
        return destinations.contains(targetPosition);
    }

    @Override
    public boolean isPathRestricted(Position destination, List<Position> piecePositions) {
        return false;
    }

    private List<Position> setupDestinations() {
        return PalaceMoveRule.moveRoutesOf(position).stream()
                .map(MoveRoute::destination)
                .toList();
    }
}
