package janggi.piece.movement;

import janggi.position.Position;

public class SoldierMovementRule implements MovementRule {

    private final MovementRule oneBlockMovementRule;
    private final MovementRule straightMovementRule;

    public SoldierMovementRule() {
        this.oneBlockMovementRule = new OneBlockMovementRule();
        this.straightMovementRule = new StraightMovementRule();
    }

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        oneBlockMovementRule.validateMovementRule(start, end);
        straightMovementRule.validateMovementRule(start, end);
    }
}
