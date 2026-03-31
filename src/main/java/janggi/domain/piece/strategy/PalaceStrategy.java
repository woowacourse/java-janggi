package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public class PalaceStrategy extends SingleStepStraightStrategy {

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        camp.validatePalace(destination);

        return super.findPath(source, destination, camp);
    }
}
