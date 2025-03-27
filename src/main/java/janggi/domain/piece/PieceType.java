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
    SOLDIER(2, new MoveRule(SoldierPathStrategy.getInstance(), DefaultMoveStrategy.getInstance())),
    HORSE(5, new MoveRule(HorsePathStrategy.getInstance(), DefaultMoveStrategy.getInstance())),
    CHARIOT(13, new MoveRule(StraightPathStrategy.getInstance(), DefaultMoveStrategy.getInstance())),
    ELEPHANT(3, new MoveRule(ElephantPathStrategy.getInstance(), DefaultMoveStrategy.getInstance())),
    CANNON(7, new MoveRule(StraightPathStrategy.getInstance(), CannonMoveStrategy.getInstance())),
    GENERAL(0, new MoveRule(PalacePathStrategy.getInstance(), DefaultMoveStrategy.getInstance())),
    GUARD(3, new MoveRule(PalacePathStrategy.getInstance(), DefaultMoveStrategy.getInstance())),
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
