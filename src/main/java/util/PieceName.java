package util;

import domain.piece.PieceType;
import domain.piece.Team;

import java.util.Arrays;

public enum PieceName {

    CHARIOT(PieceType.CHARIOT, "차"),
    HORSE(PieceType.HORSE, "마"),
    ELEPHANT(PieceType.ELEPHANT, "상"),
    GUARD(PieceType.GUARD, "사"),
    KING(PieceType.GENERAL, "궁"),
    CANNON(PieceType.CANNON, "포"),
    SOLDIER(PieceType.SOLDIER, "졸"),
    EMPTY(PieceType.EMPTY, "ㆍ");

    private static final String INVALID_PIECE = "알 수 없는 기물입니다: ";

    private final PieceType pieceType;
    private final String name;

    PieceName(PieceType pieceType, String name) {
        this.pieceType = pieceType;
        this.name = name;
    }

    public static String display(PieceType type, Team team) {
        String name = Arrays.stream(values())
                .filter(pieceName -> pieceName.pieceType == type)
                .map(pieceName -> pieceName.name)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_PIECE));

        if (team == Team.HAN) return ConsoleColor.RED + name + ConsoleColor.RESET;
        if (team == Team.CHO) return ConsoleColor.GREEN + name + ConsoleColor.RESET;
        return name;
    }
}
