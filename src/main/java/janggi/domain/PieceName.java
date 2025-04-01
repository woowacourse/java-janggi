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

    HAN_CANNON(new Cannon(Side.HAN), "포"),
    CHO_CANNON(new Cannon(Side.CHO), "포"),
    HAN_CHARIOT(new Chariot(Side.HAN), "차"),
    CHO_CHARIOT(new Chariot(Side.CHO), "차"),
    HAN_ELEPHANT(new Elephant(Side.HAN), "상"),
    CHO_ELEPHANT(new Elephant(Side.CHO), "상"),
    HAN_GENERAL(new General(Side.HAN), "궁"),
    CHO_GENERAL(new General(Side.CHO), "궁"),
    HAN_GUARD(new Guard(Side.HAN), "사"),
    CHO_GUARD(new Guard(Side.CHO), "사"),
    HAN_HORSE(new Horse(Side.HAN), "마"),
    CHO_HORSE(new Horse(Side.CHO), "마"),
    HAN_SOLDIER(new Soldier(Side.HAN), "병"),
    CHO_SOLDIER(new Soldier(Side.CHO), "병"),
    ;

    private final Piece piece;
    private final String name;

    PieceName(Piece piece, String name) {
        this.piece = piece;
        this.name = name;
    }

    public static String getName(Piece piece) {
        return Arrays.stream(PieceName.values())
                .filter(pieceName -> pieceName.piece.equals(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Piece입니다."))
                .name;
    }

    public static Piece getPiece(String name, String side) {
        Side targetSide = Side.valueOf(side);
        return Arrays.stream(PieceName.values())
                .filter(pieceName -> pieceName.name.equals(name))
                .filter(pieceName -> pieceName.piece.isSameSide(targetSide))
                .findFirst()
                .orElseThrow()
                .piece;
    }
}
