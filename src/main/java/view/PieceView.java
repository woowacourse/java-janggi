package view;

import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.Arrays;

public enum PieceView {

    GENERAL(PieceType.GENERAL, "［왕］"),
    CHARIOT(PieceType.CHARIOT, "［차］"),
    CANNON(PieceType.CANNON, "［포］"),
    HORSE(PieceType.HORSE, "［마］"),
    ELEPHANT(PieceType.ELEPHANT, "［상］"),
    GUARD(PieceType.GUARD, "［사］"),
    SOLDIER(PieceType.SOLDIER, "［졸］"),
    NONE(PieceType.NONE, "  .  "),
    ;

    private final PieceType pieceType;
    private final String viewMessage;

    PieceView(PieceType pieceType, String viewMessage) {
        this.pieceType = pieceType;
        this.viewMessage = viewMessage;
    }

    public String getViewMessage() {
        return this.viewMessage;
    }

    public static PieceView valueOf(Piece piece) {
        PieceType pieceType = piece.pieceType();
        return Arrays.stream(PieceView.values())
                .filter(pieceView -> pieceView.pieceType == pieceType)
                .findFirst()
                .orElse(NONE);
    }

}
