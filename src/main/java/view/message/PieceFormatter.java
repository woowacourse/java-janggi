package view.message;

import domain.piece.PieceType;

public enum PieceFormatter {

    PAWN(PieceType.PAWN, "卒"),
    HORSE(PieceType.HORSE, "馬"),
    ELEPHANT(PieceType.ELEPHANT, "象"),
    CHARIOT(PieceType.CHARIOT, "車"),
    CANNON(PieceType.CANNON, "包"),
    GUARD(PieceType.GUARD, "士"),
    KING(PieceType.KING, "將"),
    EMPTY(PieceType.EMPTY, "ㅡ");

    private final PieceType type;
    private final String name;

    PieceFormatter(PieceType type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String from(PieceType pieceType) {
        for (PieceFormatter piece : values()) {
            if (piece.type == pieceType) {
                return piece.name;
            }
        }

        throw new IllegalArgumentException("일치하는 타입이 없습니다.");
    }
}
