package view;

import domain.piece.PieceType;

public enum PieceTypeView {

    WANG("왕"),
    SA("사"),
    CHA("차"),
    SANG("상"),
    MA("마"),
    PO("포"),
    BYEONG("병"),
    ;

    private final String title;

    PieceTypeView(final String title) {
        this.title = title;
    }

    public static String title(final PieceType pieceType) {
        return PieceTypeView.valueOf(pieceType.name()).title;
    }
}
