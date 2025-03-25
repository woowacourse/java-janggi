package janggi.domain.rule;

import janggi.domain.Placement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.rule.block.BlockStrategy;
import janggi.domain.rule.move.MoveStrategy;
import java.util.Objects;

public class MoveRule {

    private final MoveStrategy moveStrategy;
    private final BlockStrategy blockStrategy;

    public MoveRule(final MoveStrategy moveStrategy, final BlockStrategy blockStrategy) {
        Objects.requireNonNull(moveStrategy, "moveStrategy는 null일 수 없습니다");
        Objects.requireNonNull(blockStrategy, "blockStrategy는 null일 수 없습니다");
        this.moveStrategy = moveStrategy;
        this.blockStrategy = blockStrategy;
    }

    public void validateMoveIsAllowed(final Position departure, final Position destination, final Movement movement) {
        moveStrategy.validateCorrectRule(departure, destination, movement);
    }

    public void validateRouteIsNotBlocked(final Placement placement, final Route route) {
        blockStrategy.validateIsBlock(placement, route);
    }
}
