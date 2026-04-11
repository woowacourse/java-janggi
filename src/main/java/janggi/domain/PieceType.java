package janggi.domain;

import janggi.domain.moveRules.MoveRule;
import janggi.domain.moveRules.outOfPalaceMoveRules.JumpMoveRule;
import janggi.domain.moveRules.palaceMoveRules.ChaPalaceMoveRule;
import janggi.domain.moveRules.palaceMoveRules.KingAndSaPalaceMoveRule;
import janggi.domain.moveRules.palaceMoveRules.PoPalaceMoveRule;
import janggi.domain.moveRules.palaceMoveRules.ZolPalaceMoveRule;

public enum PieceType {
    KING(new KingAndSaPalaceMoveRule()),
    SA(new KingAndSaPalaceMoveRule()),
    SANG(new JumpMoveRule()),
    MA(new JumpMoveRule()),
    CHA(new ChaPalaceMoveRule()),
    PO(new PoPalaceMoveRule()),
    ZOL(new ZolPalaceMoveRule());

    private final MoveRule moveRule;

    PieceType(MoveRule moveRule) {
        this.moveRule = moveRule;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }
}
