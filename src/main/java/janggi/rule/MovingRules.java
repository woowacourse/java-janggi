package janggi.rule;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public final class MovingRules {

    private final List<MovingRule> movingRules;

    public MovingRules(final List<MovingRule> movingRules) {
        this.movingRules = movingRules;
    }

    public MovingRule findMatchRule(final Position start, final Position end) {
        final MoveVector startEndDiff = end.calculateVectorDiff(start);
        for (MovingRule movingRule : movingRules) {
            final MoveVector vectorSum = movingRule.sumUnit();
            if (vectorSum.equals(startEndDiff)) {
                return movingRule;
            }
        }
        throw new IllegalStateException();
    }

    public boolean cannotFindRule(final Position start, final Position end) {
        final List<MoveVector> positionDiffs = new ArrayList<>();
        for (MovingRule movingRule : movingRules) {
            positionDiffs.add(movingRule.sumUnit());
        }
        return !positionDiffs.contains(end.calculateVectorDiff(start));
    }
}
