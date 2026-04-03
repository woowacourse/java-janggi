package janggi.domain.piece.strategy;

import janggi.domain.board.Palace;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public class PalaceStrategy extends SingleStepStraightStrategy {

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        Palace.validateFriendlyPalace(camp, destination);

        return super.findPath(source, destination, camp);
    }
}
