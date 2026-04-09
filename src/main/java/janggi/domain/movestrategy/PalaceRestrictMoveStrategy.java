package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.rule.MoveRule;
import janggi.domain.palace.Palace;

import java.util.List;

public class PalaceRestrictMoveStrategy extends AbstractMoveStrategy {

    private final Palace palace;

    public PalaceRestrictMoveStrategy(Palace palace, List<MoveRule> moveRules) {
        super(moveRules);
        this.palace = palace;
    }

    @Override
    public boolean canMove(Position from, Position to) {
        if (!palace.isInRange(to)) {
            return false;
        }
        return super.canMove(from, to);
    }
}
