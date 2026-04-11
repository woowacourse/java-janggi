package janggi.domain;

import janggi.domain.moveRules.ChaPalaceMoveRule;
import janggi.domain.moveRules.JumpMoveRule;
import janggi.domain.moveRules.MoveRule;
import janggi.domain.moveRules.OnceMoveRule;
import janggi.domain.moveRules.PoPalaceMoveRule;

public enum PieceType {
    KING(new OnceMoveRule()),
    SA(new OnceMoveRule()),
    SANG(new JumpMoveRule()),
    MA(new JumpMoveRule()),
    CHA(new ChaPalaceMoveRule()),
    PO(new PoPalaceMoveRule()),
    ZOL(new OnceMoveRule());

    private final MoveRule moveRule;

    PieceType(MoveRule moveRule) {
        this.moveRule = moveRule;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }
}
