package janggi.domain;

import janggi.domain.moveRules.ChaMoveRule;
import janggi.domain.moveRules.KingMoveRule;
import janggi.domain.moveRules.MaMoveRule;
import janggi.domain.moveRules.MoveRule;
import janggi.domain.moveRules.PoMoveRule;
import janggi.domain.moveRules.SaMoveRule;
import janggi.domain.moveRules.SangMoveRule;
import janggi.domain.moveRules.ZolMoveRule;

public enum PieceType {
    KING(new KingMoveRule()),
    SA(new SaMoveRule()),
    SANG(new SangMoveRule()),
    MA(new MaMoveRule()),
    CHA(new ChaMoveRule()),
    PO(new PoMoveRule()),
    ZOL(new ZolMoveRule());

    private final MoveRule moveRule;

    PieceType(MoveRule moveRule) {
        this.moveRule = moveRule;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }
}
