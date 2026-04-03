package janggi.domain.piece.strategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public class SoldierStrategy extends SingleStepStraightStrategy {

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);
        camp.validateForwardDirection(directionInformation.calculateRowDirection());

        return super.findPath(source, destination, camp);
    }
}
