package janggi.domain.piece;

import janggi.domain.moveRule.MoveRule;
import janggi.domain.moveRule.moveStrategy.ElephantMoveStrategy;
import janggi.domain.moveRule.moveStrategy.HorseMoveStrategy;
import janggi.domain.moveRule.moveStrategy.PalaceMoveStrategy;
import janggi.domain.moveRule.moveStrategy.SoldierMoveStrategy;
import janggi.domain.moveRule.moveStrategy.StraightMoveStrategy;
import janggi.domain.moveRule.routeValidator.CannonRouteValidator;
import janggi.domain.moveRule.routeValidator.DefaultRouteValidator;

public enum PieceType {
    SOLDIER(2, new MoveRule(SoldierMoveStrategy.getInstance(), DefaultRouteValidator.getInstance())),
    HORSE(5, new MoveRule(HorseMoveStrategy.getInstance(), DefaultRouteValidator.getInstance())),
    CHARIOT(13, new MoveRule(StraightMoveStrategy.getInstance(), DefaultRouteValidator.getInstance())),
    ELEPHANT(3, new MoveRule(ElephantMoveStrategy.getInstance(), DefaultRouteValidator.getInstance())),
    CANNON(7, new MoveRule(StraightMoveStrategy.getInstance(), CannonRouteValidator.getInstance())),
    GENERAL(0, new MoveRule(PalaceMoveStrategy.getInstance(), DefaultRouteValidator.getInstance())),
    GUARD(3, new MoveRule(PalaceMoveStrategy.getInstance(), DefaultRouteValidator.getInstance())),
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
