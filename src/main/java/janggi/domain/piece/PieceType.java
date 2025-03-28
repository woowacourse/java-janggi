package janggi.domain.piece;

import janggi.domain.moveRule.MoveRule;
import janggi.domain.moveRule.pathStrategy.ElephantPathStrategy;
import janggi.domain.moveRule.pathStrategy.HorsePathStrategy;
import janggi.domain.moveRule.pathStrategy.PalacePathStrategy;
import janggi.domain.moveRule.pathStrategy.SoldierPathStrategy;
import janggi.domain.moveRule.pathStrategy.StraightPathStrategy;
import janggi.domain.moveRule.moveStrategy.CannonMoveStrategy;
import janggi.domain.moveRule.moveStrategy.DefaultMoveStrategy;

public enum PieceType {
    SOLDIER(2, new MoveRule(new SoldierPathStrategy(), new DefaultMoveStrategy())),
    HORSE(5, new MoveRule(new HorsePathStrategy(), new DefaultMoveStrategy())),
    CHARIOT(13, new MoveRule(new StraightPathStrategy(), new DefaultMoveStrategy())),
    ELEPHANT(3, new MoveRule(new ElephantPathStrategy(), new DefaultMoveStrategy())),
    CANNON(7, new MoveRule(new StraightPathStrategy(),new  CannonMoveStrategy())),
    GENERAL(0, new MoveRule(new PalacePathStrategy(), new DefaultMoveStrategy())),
    GUARD(3, new MoveRule(new PalacePathStrategy(), new DefaultMoveStrategy())),
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
