package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.HorseStrategy;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.piece.strategy.MultiStepStraightStrategy;
import janggi.domain.piece.strategy.SingleStepStraightStrategy;
import janggi.domain.piece.strategy.SoldierStrategy;
import java.util.List;

public enum PieceRule {

    GENERAL(new SingleStepStraightStrategy()),
    CHARIOT(new MultiStepStraightStrategy()),
    HORSE(new HorseStrategy()),
    CANNON(new MultiStepStraightStrategy()),
    GUARD(new SingleStepStraightStrategy()),
    ELEPHANT(new ElephantStrategy()),
    SOLDIER(new SoldierStrategy());

    private final MoveStrategy moveStrategy;

    PieceRule(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public List<Position> findPath(Position from, Position to, Camp camp) {
        return moveStrategy.findPath(from, to, camp);
    }
}
