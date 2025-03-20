package domain;

import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.piece.strategy.InnerElephantStrategy;
import domain.piece.strategy.LeftElephantStrategy;
import domain.piece.strategy.OuterElephantStrategy;
import domain.piece.strategy.RightElephantStrategy;
import java.util.Arrays;

public enum SetupOption {
    INNER_ELEPHANT_SETUP("1", new InnerElephantStrategy()),
    OUTER_ELEPHANT_SETUP("2", new OuterElephantStrategy()),
    RIGHT_ELEPHANT_SETUP("3", new RightElephantStrategy()),
    LEFT_ELEPHANT_SETUP("4", new LeftElephantStrategy());

    private final String option;
    private final HorseElephantSetupStrategy setupStrategy;

    SetupOption(String option, HorseElephantSetupStrategy setupStrategy) {
        this.option = option;
        this.setupStrategy = setupStrategy;
    }

    public static HorseElephantSetupStrategy findSetupStrategy(String input) {
        return Arrays.stream(values())
                .filter(value -> value.option.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 옵션 번호입니다."))
                .setupStrategy;
    }
}
