package util;

import domain.piece.Team;

import java.util.Arrays;

public enum PieceName {

    CHARIOT("Chariot", "차"),
    HORSE("Horse", "마"),
    ELEPHANT("Elephant", "상"),
    GUARD("Guard", "사"),
    KING("General", "궁"),
    CANNON("Cannon", "포"),
    SOLDIER("Soldier", "졸"),
    EMPTY("EmptyPiece", "ㆍ");

    private final String className;
    private final String name;

    PieceName(String className, String name) {
        this.className = className;
        this.name = name;
    }

    public static String from(String className, Team team) {
        String name = Arrays.stream(values())
                .filter(pieceName -> pieceName.className.equals(className))
                .map(pieceName -> pieceName.name)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_PIECE.getMessage() + className));

        if (team == Team.HAN) return ConsoleColor.RED + name + ConsoleColor.RESET;
        if (team == Team.CHO) return ConsoleColor.GREEN + name + ConsoleColor.RESET;
        return name;
    }
}
