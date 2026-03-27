package model.pieces;

import model.move.HorseMoveRule;
import model.move.MoveRule;

public enum PieceType {
    CHARIOT("차", new HorseMoveRule()),
    HORSE("마", new HorseMoveRule()),
    ELEPHANT("상", new HorseMoveRule()),
    GUARD("사", new HorseMoveRule()),
    GENERAL("장", new HorseMoveRule()),
    CANNON("포", new HorseMoveRule()),
    SOLDIER("병", new HorseMoveRule());

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
