package janggi.rule;

import janggi.position.Position;
import java.util.List;

public final class MovingRules {

    private final List<MovingRule> movingRules;

    public MovingRules(final List<MovingRule> movingRules) {
        this.movingRules = movingRules;
    }

    public MovingRule findMatchRule(final Position start, final Position end) {
        MoveVector startToEnd = end.calculateDifference(start);
        return movingRules.stream()
                .filter(movingRule -> movingRule.sumAllVectors().equals(startToEnd))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 기물의 규칙에 맞지 않는 움직임입니다."));
    }
}
