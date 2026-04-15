package view;

import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;
import java.util.function.UnaryOperator;

public enum PieceName {

    CHARIOT("차"),
    HORSE("마"),
    ELEPHANT("상"),
    GUARD("사"),
    GENERAL("궁"),
    CANNON("포"),
    SOLDIER("졸"),
    EMPTY("ㆍ");

    private static final Map<Team, UnaryOperator<String>> TEAM_TEXT_STYLES = Map.of(
            Team.CHO, value -> ConsoleColor.GREEN + value + ConsoleColor.RESET,
            Team.HAN, value -> ConsoleColor.RED + value + ConsoleColor.RESET,
            Team.NONE, UnaryOperator.identity()
    );

    private final String name;

    PieceName(String name) {
        this.name = name;
    }

    public static String from(Piece piece) {
        return TEAM_TEXT_STYLES.get(piece.team())
                .apply(PieceName.valueOf(piece.pieceType().name()).name);
    }
}
