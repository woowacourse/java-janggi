package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import java.util.Arrays;

public enum PieceName {

    HAN_CANNON(new Cannon(Side.HAN), "포", "Cannon"),
    CHO_CANNON(new Cannon(Side.CHO), "포", "Cannon"),
    HAN_CHARIOT(new Chariot(Side.HAN), "차", "Chariot"),
    CHO_CHARIOT(new Chariot(Side.CHO), "차", "Chariot"),
    HAN_ELEPHANT(new Elephant(Side.HAN), "상", "Elephant"),
    CHO_ELEPHANT(new Elephant(Side.CHO), "상", "Elephant"),
    HAN_GENERAL(new General(Side.HAN), "궁", "General"),
    CHO_GENERAL(new General(Side.CHO), "궁", "General"),
    HAN_GUARD(new Guard(Side.HAN), "사", "Guard"),
    CHO_GUARD(new Guard(Side.CHO), "사", "Guard"),
    HAN_HORSE(new Horse(Side.HAN), "마", "Horse"),
    CHO_HORSE(new Horse(Side.CHO), "마", "Horse"),
    HAN_SOLDIER(new Soldier(Side.HAN), "병", "Soldier"),
    CHO_SOLDIER(new Soldier(Side.CHO), "병", "Soldier"),
    ;

    private final Piece piece;
    private final String displayName;
    private final String databaseName;

    PieceName(Piece piece, String displayName, String databaseName) {
        this.piece = piece;
        this.displayName = displayName;
        this.databaseName = databaseName;
    }

    public static String getDisplayName(Piece piece) {
        return Arrays.stream(PieceName.values())
                .filter(pieceName -> pieceName.piece.equals(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Piece입니다."))
                .displayName;
    }

    public static Piece getPiece(String name, String side) {
        Side targetSide = Side.valueOf(side);
        return Arrays.stream(PieceName.values())
                .filter(pieceName -> pieceName.databaseName.equals(name))
                .filter(pieceName -> pieceName.piece.isSameSide(targetSide))
                .findFirst()
                .orElseThrow()
                .piece;
    }
}
