package domain;

import java.util.Arrays;
import strategy.InitializeStrategy;
import strategy.InnerElephantFormationStrategy;
import strategy.LeftElephantFormationStrategy;
import strategy.OuterElephantFormationStrategy;
import strategy.RightElephantFormationStrategy;

public enum HorseElephantFormation {
    INNER_ELEPHANT("마상상마") {
        @Override
        public InitializeStrategy createStrategy() {
            return new InnerElephantFormationStrategy();
        }
    },
    OUTER_ELEPHANT("상마마상") {
        @Override
        public InitializeStrategy createStrategy() {
            return new OuterElephantFormationStrategy();
        }
    },
    RIGHT_ELEPHANT("마상마상"){
        @Override
        public InitializeStrategy createStrategy() {
            return new RightElephantFormationStrategy();
        }
    },
    LEFT_ELEPHANT("상마상마"){
        @Override
        public InitializeStrategy createStrategy() {
            return new LeftElephantFormationStrategy();
        }
    };

    private final String pattern;

    HorseElephantFormation(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 각 상/마 패턴에 맞는 전략 생성
     */
    public abstract InitializeStrategy createStrategy();

    public static HorseElephantFormation from(String pattern) {
        return Arrays.stream(values())
                .filter(type -> type.pattern.equals(pattern))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포메이션입니다: " + pattern));
    }
}