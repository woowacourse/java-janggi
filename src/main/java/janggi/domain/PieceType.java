package janggi.domain;

import janggi.domain.moveRules.ChaMoveRule;
import janggi.domain.moveRules.JumpMoveRule;
import janggi.domain.moveRules.MoveRule;
import janggi.domain.moveRules.OnceMoveRule;
import janggi.domain.moveRules.PoMoveRule;

public enum PieceType {
    KING(new OnceMoveRule()),
    SA(new OnceMoveRule()),
    SANG(new JumpMoveRule()),
    MA(new JumpMoveRule()),
    CHA(new ChaMoveRule()),
    PO(new PoMoveRule()),
    ZOL(new OnceMoveRule());

    private final MoveRule moveRule;

    PieceType(MoveRule moveRule) {
        this.moveRule = moveRule;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }
}
