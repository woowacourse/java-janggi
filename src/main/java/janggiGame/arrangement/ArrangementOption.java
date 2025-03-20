package janggiGame.arrangement;

import java.util.Arrays;

public enum ArrangementOption {
    INNER_ELEPHANT(1, new InnerElephantStrategy()),
    OUTER_ELEPHANT(2, new OuterElephantStrategy()),
    LEFT_ELEPHANT(3, new LeftElephantStrategy()),
    RIGHT_ELEPHANT(4, new RightElephantStrategy());

    private final Integer option;
    private final ArrangementStrategy arrangementStrategy;

    ArrangementOption(Integer option, ArrangementStrategy arrangementStrategy) {
        this.option = option;
        this.arrangementStrategy = arrangementStrategy;
    }

    public static ArrangementOption findBy(Integer optionNum) {
        return Arrays.stream(values())
                .filter(instance -> instance.option.equals(optionNum))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하는 배치 옵션이 아닙니다."));
    }

    public ArrangementStrategy getArrangementStrategy() {
        return arrangementStrategy;
    }
}
