package domain;

import domain.strategy.CannonMoveStrategy;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.GeneralMoveStrategy;
import domain.strategy.GuardMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.SoldierMoveStrategy;

public enum Type {
    GENERAL("궁", new GeneralMoveStrategy()),
    CHARIOT("차", new ChariotMoveStrategy()),
    CANNON("포", new CannonMoveStrategy()),
    HORSE("마", new HorseMoveStrategy()),
    ELEPHANT("상", new ElephantMoveStrategy()),
    GUARD("사", new GuardMoveStrategy()),
    SOLDIER("졸", new SoldierMoveStrategy());

    private final String name;
    private final MoveStrategy strategySupplier;

    Type(String name, MoveStrategy strategySupplier) {
        this.name = name;
        this.strategySupplier = strategySupplier;
    }

    public MoveStrategy getStrategy() {
        return strategySupplier;
    }

    public String getName() {
        return name;
    }
}
