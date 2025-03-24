package view;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Arrays;

public enum PieceName {
    CANNON(PieceType.CANNON, "포"),
    CHARIOT(PieceType.CHARIOT, "차"),
    ELEPHANT(PieceType.ELEPHANT, "상"),
    GENERAL(PieceType.GENERAL, "궁"),
    GUARD(PieceType.GUARD, "사"),
    HORSE(PieceType.HORSE, "마"),
    SOLDIER(PieceType.SOLDIER, "졸"),
    EMPTY(PieceType.EMPTY, "ㅁ");

    private final PieceType pieceType;
    private final String name;

    PieceName(PieceType pieceType, String name) {
        this.pieceType = pieceType;
        this.name = name;
    }

    public static String getNameFromPieceType(Piece piece) {
        return Arrays.stream(PieceName.values())
                .filter(pieceName -> piece.isSamePieceType(pieceName.pieceType))
                .map(pieceName -> pieceName.name)
                .findAny()
                .orElse("");
    }

    public static PieceType getPieceTypeFromName(String name) {
        return Arrays.stream(PieceName.values())
                .filter(pieceName -> pieceName.name.equals(name))
                .map(pieceName -> pieceName.pieceType)
                .findAny()
                .orElse(PieceType.EMPTY);
    }
}
