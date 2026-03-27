package model.pieces;

import model.move.*;

public enum PieceType {
    CHARIOT("차", new ChariotMoveRule()),
    HORSE("마", new HorseMoveRule()),
    ELEPHANT("상", new ElephantMoveRule()),
    GUARD("사", new GuardMoveRule()),
    GENERAL("장", new GeneralMoveRule()),
    CANNON("포", new CannonMoveRule()),
    SOLDIER("병", new SoldierMoveRule());

    private final String symbol;
    private final MoveRule rule;

    PieceType(String symbol, MoveRule rule) {
        this.symbol = symbol;
        this.rule = rule;
    }

    public String symbol() {
        return symbol;
    }

    public MoveRule rule() {
        return rule;
    }
}
