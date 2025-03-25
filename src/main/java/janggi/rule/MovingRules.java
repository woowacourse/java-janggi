package janggi.rule;

import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public final class MovingRules {

    private final List<MovingRule> movingRules;
    private final List<MovingRule> extraPalaceMovingRules;

    public MovingRules(final List<MovingRule> movingRules, final List<MovingRule> extraPalaceMovingRules) {
        this.movingRules = movingRules;
        this.extraPalaceMovingRules = extraPalaceMovingRules;
    }

    public MovingRule findMatchRule(final Position start, final Position end) {
        final MoveVector startEndDiff = end.calculateVectorDiff(start);
        for (MovingRule movingRule : movingRules) {
            final MoveVector vectorSum = movingRule.sumUnit();
            if (vectorSum.equals(startEndDiff)) {
                return movingRule;
            }
        }
        if (start.isPalace() && end.isPalace()) {
            for (MovingRule movingRule : extraPalaceMovingRules) {
                final MoveVector vectorSum = movingRule.sumUnit();
                if (vectorSum.equals(startEndDiff)) {
                    return movingRule;
                }
            }
        }
        throw new IllegalStateException("[ERROR] 프로그램 로직이 잘못됐습니다.");
    }

    public boolean cannotFindRule(final Position start, final Position end) {
        List<MovingRule> totalMovingRules = new ArrayList<>();
        if (start.isPalace() && end.isPalace()) {
            totalMovingRules.addAll(extraPalaceMovingRules);
        }
        totalMovingRules.addAll(movingRules);
        final List<MoveVector> positionDiffs = new ArrayList<>();
        for (MovingRule movingRule : totalMovingRules) {
            positionDiffs.add(movingRule.sumUnit());
        }
        return !positionDiffs.contains(end.calculateVectorDiff(start));
    }
}
