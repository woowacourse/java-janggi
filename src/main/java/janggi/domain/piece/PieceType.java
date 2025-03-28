package janggi.domain.piece;

import janggi.domain.moveRule.MoveRule;
import janggi.domain.moveRule.movementStrategy.ElephantMovementStrategy;
import janggi.domain.moveRule.movementStrategy.HorseMovementStrategy;
import janggi.domain.moveRule.movementStrategy.PalaceMovementStrategy;
import janggi.domain.moveRule.movementStrategy.SoldierMovementStrategy;
import janggi.domain.moveRule.movementStrategy.StraightMovementStrategy;
import janggi.domain.moveRule.routeStrategy.CannonRouteStrategy;
import janggi.domain.moveRule.routeStrategy.DefaultRouteStrategy;

public enum PieceType {
    SOLDIER(2, new MoveRule(new SoldierMovementStrategy(), new DefaultRouteStrategy())),
    HORSE(5, new MoveRule(new HorseMovementStrategy(), new DefaultRouteStrategy())),
    CHARIOT(13, new MoveRule(new StraightMovementStrategy(), new DefaultRouteStrategy())),
    ELEPHANT(3, new MoveRule(new ElephantMovementStrategy(), new DefaultRouteStrategy())),
    CANNON(7, new MoveRule(new StraightMovementStrategy(),new CannonRouteStrategy())),
    GENERAL(0, new MoveRule(new PalaceMovementStrategy(), new DefaultRouteStrategy())),
    GUARD(3, new MoveRule(new PalaceMovementStrategy(), new DefaultRouteStrategy())),
    NONE(0, null)
    ;

    private final int score;
    private final MoveRule moveRule;

    PieceType(int score, MoveRule moveRule) {
        this.score = score;
        this.moveRule = moveRule;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }

    public int getScore() {
        return score;
    }
}
