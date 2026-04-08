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
    GENERAL("궁", new GeneralMoveStrategy(), 0),
    CHARIOT("차", new ChariotMoveStrategy(), 13),
    CANNON("포", new CannonMoveStrategy(), 7),
    HORSE("마", new HorseMoveStrategy(), 5),
    ELEPHANT("상", new ElephantMoveStrategy(), 3),
    GUARD("사", new GuardMoveStrategy(), 3),
    SOLDIER("졸", new SoldierMoveStrategy(), 2);

    private final String name;
    private final MoveStrategy strategy;
    private final int score;

    Type(String name, MoveStrategy strategy, int score) {
        this.name = name;
        this.strategy = strategy;
        this.score = score;
    }

    public MoveStrategy getStrategy() {
        return strategy;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
