package view;

import domain.piece.*;

public enum PieceTypeName {
    CANNON(PieceType.CANNON, "포"),
    CHARIOT(PieceType.CHARIOT, "차"),
    ELEPHANT(PieceType.ELEPHANT, "상"),
    GENERAL(PieceType.GENERAL, "궁"),
    GUARD(PieceType.GUARD, "사"),
    HORSE(PieceType.HORSE, "마"),
    SOLDIER(PieceType.SOLDIER, "졸"),
    EMPTY(PieceType.NONE, "ㅁ")
    ;

    private final PieceType pieceType;
    private final String name;

    PieceTypeName(PieceType pieceType, String name) {
        this.pieceType = pieceType;
        this.name = name;
    }

    public static String getNameFrom(Piece piece) {
        for(PieceTypeName pieceTypeName : PieceTypeName.values()) {
            if(piece.getType() == pieceTypeName.pieceType) {
                String colorString = pieceTypeName.getColoSetString(piece);
                return colorString + pieceTypeName.name;
            }
        }

        return EMPTY.name;
    }

    public static PieceType getTypeFrom(String name) {
        for(PieceTypeName pieceTypeName : PieceTypeName.values()) {
            if(name.equals(pieceTypeName.name)) {
                return pieceTypeName.pieceType;
            }
        }
        throw new IllegalArgumentException("잘못된 기물의 이름입니다.");
    }

    private String getColoSetString(Piece piece) {
        PieceColor color = piece.getColor();
        if(color == PieceColor.RED) {
            return "\u001B[31m";
        }
        if(color == PieceColor.BLUE) {
            return "\u001B[34m";
        }
        return "\u001B[0m";
    }

}
