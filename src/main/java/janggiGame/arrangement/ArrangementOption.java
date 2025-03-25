package janggiGame.arrangement;

import java.util.Arrays;
import java.util.Objects;

public enum ArrangementOption {
    INNER_ELEPHANT(1, new InnerElephantStrategy()),
    OUTER_ELEPHANT(2, new OuterElephantStrategy()),
    LEFT_ELEPHANT(3, new LeftElephantStrategy()),
    RIGHT_ELEPHANT(4, new RightElephantStrategy());

    private final Integer option;
    private final ArrangementStrategy arrangementStrategy;

    ArrangementOption(final Integer option, final ArrangementStrategy arrangementStrategy) {
        this.option = Objects.requireNonNull(option);
        this.arrangementStrategy = Objects.requireNonNull(arrangementStrategy);
    }

    public static ArrangementOption findBy(final Integer optionNum) {
        return Arrays.stream(values())
                .filter(instance -> instance.option.equals(optionNum))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재 하는 않는 배치 옵션 입니다."));
    }

    public ArrangementStrategy getArrangementStrategy() {
        return arrangementStrategy;
    }
}
