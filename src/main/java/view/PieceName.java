package view;

import domain.piece.*;

import java.util.Arrays;

public enum PieceName {
    CANNON(Cannon.class, "포"),
    CHARIOT(Chariot.class, "차"),
    ELEPHANT(Elephant.class, "상"),
    GENERAL(General.class, "궁"),
    GUARD(Guard.class, "사"),
    HORSE(Horse.class, "마"),
    SOLDIER(Soldier.class, "졸"),
    EMPTY(Empty.class, "ㅁ")
    ;

    private final Class<? extends Piece> pieceType;
    private final String name;

    PieceName(Class<? extends Piece> pieceType, String name) {
        this.pieceType = pieceType;
        this.name = name;
    }

    public static String getNameFromPiece(Piece piece) {
        for(PieceName pieceName : PieceName.values()) {
            if(pieceName.pieceType.equals(piece.getClass())) {
                String colorString = pieceName.getColorString(piece);
                return colorString + pieceName.name;
            }
        }

        return "";
    }

    public static Class<? extends Piece> pieceTypeFromName(String name) {
        for(PieceName pieceName : PieceName.values()) {
            if(pieceName.name.equals(name)) {
                return pieceName.pieceType;
            }
        }
        return Empty.class;
    }

    private String getColorString(Piece piece) {
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
