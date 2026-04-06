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
    KING("왕", new KingMoveRule()),
    SA("사", new SaMoveRule()),
    SANG("상", new SangMoveRule()),
    MA("마", new MaMoveRule()),
    CHA("차", new ChaMoveRule()),
    PO("포", new PoMoveRule()),
    ZOL("졸", new ZolMoveRule());

    private final String name;
    private final MoveRule moveRule;

    PieceType(String name, MoveRule moveRule) {
        this.name = name;
        this.moveRule = moveRule;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }
}
