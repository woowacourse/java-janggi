package janggi.domain.rule;

import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.rule.block.BlockStrategy;
import janggi.domain.rule.move.MoveStrategy;

public class MoveRule {

    private final MoveStrategy moveStrategy;
    private final BlockStrategy blockStrategy;

    public MoveRule(final MoveStrategy moveStrategy, final BlockStrategy blockStrategy) {
        this.moveStrategy = moveStrategy;
        this.blockStrategy = blockStrategy;
    }

    public void validateMove(final Position departure, final Position destination, final Movement movement) {
        moveStrategy.validateCorrectRule(departure, destination, movement);
    }

    public void validateBlock(final Placement placement, final Route route) {
        blockStrategy.validateIsBlock(placement, route);
    }
}
