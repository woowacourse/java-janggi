package view.label;

import domain.game.Side;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Soldier;
import java.util.Arrays;

public enum PieceLabel {
    CANNON(Cannon.class, "포", "包"),
    CHARIOT(Chariot.class, "차", "車"),
    ELEPHANT(Elephant.class, "상", "象"),
    HORSE(Horse.class, "마", "馬"),
    SOLDIER(Soldier.class, "졸", "兵"),
    GUARD(Guard.class, "사", "士"),
    GENERAL(General.class, "초", "漢"),
    ;

    private final Class<? extends Piece> pieceClass;
    private final String choLabel;
    private final String hanLabel;

    PieceLabel(
            Class<? extends Piece> pieceClass,
            String choLabel,
            String hanLabel
    ) {
        this.pieceClass = pieceClass;
        this.choLabel = choLabel;
        this.hanLabel = hanLabel;
    }

    public static String getLabel(Piece piece) {
        return Arrays.stream(values())
                .filter(label -> label.isSameType(piece))
                .map(label -> label.findLabelBySide(piece))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(piece + "의 라벨을 정의해야 합니다."));
    }

    private boolean isSameType(Piece piece) {
        return pieceClass.equals(piece.getClass());
    }

    private String findLabelBySide(Piece piece) {
        if (piece.hasSameSide(Side.CHO)) {
            return choLabel;
        }

        return hanLabel;
    }
}
