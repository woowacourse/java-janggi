package janggi.domain.piece;

import janggi.domain.piece.condition.EmptyCondition;
import janggi.domain.piece.condition.MoveCondition;
import janggi.domain.piece.condition.OnePieceExistsCondition;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.HorseStrategy;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.piece.strategy.MultiStepStraightStrategy;
import janggi.domain.piece.strategy.SingleStepStraightStrategy;
import janggi.domain.piece.strategy.SoldierStrategy;

public enum PieceRule {

    GENERAL(new SingleStepStraightStrategy(), new EmptyCondition()),
    CHARIOT(new MultiStepStraightStrategy(), new EmptyCondition()),
    HORSE(new HorseStrategy(), new EmptyCondition()),
    CANNON(new MultiStepStraightStrategy(), new OnePieceExistsCondition()),
    GUARD(new SingleStepStraightStrategy(), new EmptyCondition()),
    ELEPHANT(new ElephantStrategy(), new EmptyCondition()),
    SOLDIER(new SoldierStrategy(), new EmptyCondition());

    private final MoveStrategy moveStrategy;
    private final MoveCondition moveCondition;

    PieceRule(MoveStrategy moveStrategy, MoveCondition moveCondition) {
        this.moveStrategy = moveStrategy;
        this.moveCondition = moveCondition;
    }
}
