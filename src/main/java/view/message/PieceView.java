package view.message;

import domain.piece.PieceType;

public enum PieceView {

    PAWN(PieceType.PAWN, "졸"),
    CHARIOT(PieceType.CHARIOT, "차"),
    HORSE(PieceType.HORSE, "마"),
    ELEPHANT(PieceType.ELEPHANT, "상"),
    CANNON(PieceType.CANNON, "포"),
    GUARD(PieceType.GUARD, "사"),
    KING(PieceType.KING, "장"),
    EMPTY(PieceType.EMPTY, "ㅁ");

    private final PieceType type;
    private final String name;

    PieceView(PieceType type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String from(PieceType pieceType) {
        for (PieceView view : values()) {
            if (view.type == pieceType) {
                return view.name;
            }
        }
        return EMPTY.name;
    }
}
