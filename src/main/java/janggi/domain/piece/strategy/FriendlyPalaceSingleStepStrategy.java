package janggi.domain.piece.strategy;

import janggi.domain.board.Palace;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public class FriendlyPalaceSingleStepStrategy extends SingleStepStraightStrategy {

    private final Camp camp;

    public FriendlyPalaceSingleStepStrategy(Camp camp) {
        this.camp = camp;
    }

    @Override
    public List<Position> findPath(Position source, Position destination) {
        Palace.validateFriendlyPalace(camp, destination);

        return super.findPath(source, destination);
    }
}
