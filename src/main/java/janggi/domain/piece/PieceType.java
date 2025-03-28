package janggi.domain.piece;

import janggi.domain.moveRule.MoveRule;
import janggi.domain.moveRule.pathStrategy.ElephantPathStrategy;
import janggi.domain.moveRule.pathStrategy.HorsePathStrategy;
import janggi.domain.moveRule.pathStrategy.PalacePathStrategy;
import janggi.domain.moveRule.pathStrategy.SoldierPathStrategy;
import janggi.domain.moveRule.pathStrategy.StraightPathStrategy;
import janggi.domain.moveRule.routeStrategy.CannonRouteStrategy;
import janggi.domain.moveRule.routeStrategy.DefaultRouteStrategy;

public enum PieceType {
    SOLDIER(2, new MoveRule(new SoldierPathStrategy(), new DefaultRouteStrategy())),
    HORSE(5, new MoveRule(new HorsePathStrategy(), new DefaultRouteStrategy())),
    CHARIOT(13, new MoveRule(new StraightPathStrategy(), new DefaultRouteStrategy())),
    ELEPHANT(3, new MoveRule(new ElephantPathStrategy(), new DefaultRouteStrategy())),
    CANNON(7, new MoveRule(new StraightPathStrategy(),new CannonRouteStrategy())),
    GENERAL(0, new MoveRule(new PalacePathStrategy(), new DefaultRouteStrategy())),
    GUARD(3, new MoveRule(new PalacePathStrategy(), new DefaultRouteStrategy())),
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
