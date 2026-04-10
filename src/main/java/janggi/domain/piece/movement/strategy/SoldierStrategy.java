package janggi.domain.piece.movement.strategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public class SoldierStrategy extends SingleStepStraightStrategy {

    private final Camp camp;

    public SoldierStrategy(Camp camp) {
        this.camp = camp;
    }

    @Override
    public List<Position> findPath(Position source, Position destination) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);
        camp.validateForwardDirection(directionInformation.calculateRowDirection());

        return super.findPath(source, destination);
    }
}
