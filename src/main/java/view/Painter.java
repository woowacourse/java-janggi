package view;

import domain.piece.Piece;
import domain.piece.Team;

public class Painter {

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String WHITE = "\u001B[37m";

    public static String paintByTeam(Piece piece) {
        if (piece.hasTeam(Team.CHO)) {
            return GREEN;
        }

        return RED;
    }

    public static String paintWhite() {
        return WHITE;
    }
}
