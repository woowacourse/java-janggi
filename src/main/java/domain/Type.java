package domain;

import domain.strategy.CannonMoveStrategy;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.GeneralMoveStrategy;
import domain.strategy.GuardMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.SoldierMoveStrategy;
import java.util.function.Supplier;

public enum Type {
    GENERAL("궁", GeneralMoveStrategy::new),
    CHARIOT("차", ChariotMoveStrategy::new),
    CANNON("포", CannonMoveStrategy::new),
    HORSE("마", HorseMoveStrategy::new),
    ELEPHANT("상", ElephantMoveStrategy::new),
    GUARD("사", GuardMoveStrategy::new),
    SOLDIER("졸", SoldierMoveStrategy::new);

    private final String name;
    private final Supplier<MoveStrategy> strategySupplier;

    Type(String name, Supplier<MoveStrategy> strategySupplier) {
        this.name = name;
        this.strategySupplier = strategySupplier;
    }

    public MoveStrategy getStrategy() {
        return strategySupplier.get();
    }

    public String getName() {
        return name;
    }
}
