package domain.strategy;

import domain.Position;
import domain.moverule.GreenSoldierMoveRule;
import java.util.ArrayList;
import java.util.Arrays;
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
    public boolean hasPieceOnPath(Position destination, List<Position> piecePositions) {
        return false;
    }

    private List<Position> setupDestinations() {
        return Arrays.stream(GreenSoldierMoveRule.values())
                .map(greenSoldierMoveRule -> greenSoldierMoveRule.destination(position))
                .toList();
    }
}
