package janggi.moveStrategy.rule;

import janggi.position.Position;
import java.util.List;

public final class MovingRules {

    private final List<MovingRule> movingRules;
    private final List<MovingRule> extraPalaceMovingRules;
    private MovingRule matchedRule;

    public MovingRules(final List<MovingRule> movingRules, final List<MovingRule> extraPalaceMovingRules) {
        this.movingRules = movingRules;
        this.extraPalaceMovingRules = extraPalaceMovingRules;
    }

    public boolean findRule(final Position start, final Position end) {
        final MoveVector startEndDiff = end.calculateVectorDiff(start);
        for (MovingRule movingRule : movingRules) {
            final MoveVector vectorSum = movingRule.sumUnit();
            if (vectorSum.equals(startEndDiff)) {
                matchedRule = movingRule;
                return true;
            }
        }
        if (start.isPalace() && end.isPalace()) {
            for (MovingRule movingRule : extraPalaceMovingRules) {
                final MoveVector vectorSum = movingRule.sumUnit();
                if (vectorSum.equals(startEndDiff)) {
                    matchedRule = movingRule;
                    return true;
                }
            }
        }
        return false;
    }

    public MovingRule getMatchedRule() {
        return matchedRule;
    }
}
