package janggi.view.resolver;

import janggi.domain.strategy.StrategyLabel;

public class StrategyViewResolver {

    private StrategyViewResolver() {
    }

    public static String toDisplayName(StrategyLabel strategy) {
        String name = switch (strategy) {
            case HEEH -> "마상상마";
            case HEHE -> "마상마상";
            case EHEH -> "상마상마";
            case EHHE -> "상마마상";
        };

        return String.format("%d.%s", strategy.getDecisionNumber(), name);
    }
}
