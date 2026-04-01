package janggi.view;

import janggi.strategy.StrategyLabel;

public class StrategyViewResolver {

    private StrategyViewResolver() {
    }

    public static String toDisplayName(StrategyLabel strategy) {
        return switch (strategy) {
            case HEEH -> "마상상마";
            case HEHE -> "마상마상";
            case EHEH -> "상마상마";
            case EHHE -> "상마마상";
        };
    }
}
