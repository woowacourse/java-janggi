package domain;

import java.util.Arrays;
import strategy.InitializeStrategy;
import strategy.InnerElephantFormationStrategy;
import strategy.LeftElephantFormationStrategy;
import strategy.OuterElephantFormationStrategy;
import strategy.RightElephantFormationStrategy;

public enum HorseElephantFormation {
    INNER_ELEPHANT("마상상마", new InnerElephantFormationStrategy()),
    OUTER_ELEPHANT("상마마상", new OuterElephantFormationStrategy()),
    RIGHT_ELEPHANT("마상마상", new RightElephantFormationStrategy()),
    LEFT_ELEPHANT("상마상마", new LeftElephantFormationStrategy());

    private final String pattern;
    private final InitializeStrategy strategy;

    HorseElephantFormation(String pattern, InitializeStrategy strategy) {
        this.pattern = pattern;
        this.strategy = strategy;
    }

    public String getPattern(){
        return this.pattern;
    }

    public InitializeStrategy getStrategy() {
        return this.strategy;
    }

    public static HorseElephantFormation getFormationFrom(String pattern) {
        return Arrays.stream(values())
                .filter(type -> type.pattern.equals(pattern))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포메이션입니다: " + pattern));
    }
}
