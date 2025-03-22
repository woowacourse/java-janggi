package janggi.common;

import janggi.domain.Side;
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

    HAN_CANNON(new Cannon(Side.HAN), "\u001B[31m포\u001B[0m"),
    CHO_CANNON(new Cannon(Side.CHO), "\u001B[32m포\u001B[0m"),
    HAN_CHARIOT(new Chariot(Side.HAN), "\u001B[31m차\u001B[0m"),
    CHO_CHARIOT(new Chariot(Side.CHO), "\u001B[32m차\u001B[0m"),
    HAN_ELEPHANT(new Elephant(Side.HAN), "\u001B[31m상\u001B[0m"),
    CHO_ELEPHANT(new Elephant(Side.CHO), "\u001B[32m상\u001B[0m"),
    HAN_GENERAL(new General(Side.HAN), "\u001B[31m궁\u001B[0m"),
    CHO_GENERAL(new General(Side.CHO), "\u001B[32m궁\u001B[0m"),
    HAN_GUARD(new Guard(Side.HAN), "\u001B[31m사\u001B[0m"),
    CHO_GUARD(new Guard(Side.CHO), "\u001B[32m사\u001B[0m"),
    HAN_HORSE(new Horse(Side.HAN), "\u001B[31m마\u001B[0m"),
    CHO_HORSE(new Horse(Side.CHO), "\u001B[32m마\u001B[0m"),
    HAN_SOLDIER(new Soldier(Side.HAN), "\u001B[31m병\u001B[0m"),
    CHO_SOLDIER(new Soldier(Side.CHO), "\u001B[32m병\u001B[0m"),
    ;

    private final Piece piece;
    private final String name;

    PieceName(Piece piece, String name) {
        this.piece = piece;
        this.name = name;
    }

    public static String findName(Piece piece) {
        return Arrays.stream(PieceName.values())
                .filter(pieceName -> pieceName.piece.equals(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Piece입니다."))
                .name;
    }
}
