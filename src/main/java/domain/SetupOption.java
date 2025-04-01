package domain;

import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.piece.strategy.InnerElephantStrategy;
import domain.piece.strategy.LeftElephantStrategy;
import domain.piece.strategy.OuterElephantStrategy;
import domain.piece.strategy.RightElephantStrategy;

public enum SetupOption {

    INNER_ELEPHANT_SETUP("안상 배치", new InnerElephantStrategy()),
    OUTER_ELEPHANT_SETUP("바깥상 배치", new OuterElephantStrategy()),
    RIGHT_ELEPHANT_SETUP("우상 배치", new RightElephantStrategy()),
    LEFT_ELEPHANT_SETUP("좌상 배치", new LeftElephantStrategy());

    private final String description;
    private final HorseElephantSetupStrategy setupStrategy;

    SetupOption(String description, HorseElephantSetupStrategy setupStrategy) {
        this.description = description;
        this.setupStrategy = setupStrategy;
    }

    public HorseElephantSetupStrategy getSetupStrategy() {
        return setupStrategy;
    }

    public String getDescription() {
        return description;
    }
}
