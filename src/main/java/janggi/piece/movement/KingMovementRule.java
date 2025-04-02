package janggi.piece.movement;

import janggi.position.Position;

public class KingMovementRule implements MovementRule {

    private final MovementRule straightMovementRule;
    private final MovementRule oneBlockMovementRule;
    private final MovementRule palaceMovementRule;

    public KingMovementRule() {
        this.straightMovementRule = new StraightMovementRule();
        this.oneBlockMovementRule = new OneBlockMovementRule();
        this.palaceMovementRule = new PalaceMovementRule();
    }

    @Override
    public void validateMovementRule(Position start, Position end) {
        straightMovementRule.validateMovementRule(start, end);
        oneBlockMovementRule.validateMovementRule(start, end);
        palaceMovementRule.validateMovementRule(start, end);
    }
}
