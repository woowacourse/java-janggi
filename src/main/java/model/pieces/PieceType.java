package model.pieces;

import model.move.CannonMoveRule;
import model.move.ChariotMoveRule;
import model.move.ElephantMoveRule;
import model.move.GeneralMoveRule;
import model.move.GuardMoveRule;
import model.move.HorseMoveRule;
import model.move.MoveRule;
import model.move.SoldierMoveRule;

public enum PieceType {
    CHARIOT("차", 13, new ChariotMoveRule(), "R"),
    CANNON("포", 7, new CannonMoveRule(), "C"),
    HORSE("마", 5, new HorseMoveRule(), "H"),
    ELEPHANT("상", 3, new ElephantMoveRule(), "E"),
    GUARD("사", 3, new GuardMoveRule(), "G"),
    SOLDIER("병", 2, new SoldierMoveRule(), "P"),
    GENERAL("장", 0, new GeneralMoveRule(), "K");

    private final String symbol;
    private final int score;
    private final MoveRule rule;
    private final String label;

    PieceType(String symbol, int score, MoveRule rule, String label) {
        this.symbol = symbol;
        this.score = score;
        this.rule = rule;
        this.label = label;
    }

    public static PieceType fromType(String name) {
        for (PieceType pieceType : PieceType.values()) {
            if (pieceType.name().equals(name)) {
                return pieceType;
            }
        }
        throw new IllegalArgumentException("[ERROR] 없는 기물입니다.");
    }

    public String symbol() {
        return symbol;
    }

    public int score() {
        return score;
    }

    public MoveRule rule() {
        return rule;
    }

    public String label() {
        return label;
    }
}
