package janggi.view;

import janggi.domain.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import java.util.Map;

public class OutputView {
    private static final String COLOR_CODE_EXIT = "\u001B[0m";
    private static final String COLOR_CODE_BLUE = "\u001B[34m";
    private static final String COLOR_CODE_RED = "\u001B[31m";
    private static final String COLOR_CODE_YELLOW = "\u001B[33m";

    public void printBoard(Board board) {
        System.out.print("   ");
        for (int j = 1; j <= 9; j++) {
            System.out.printf("%4s", j + "");
        }
        System.out.println();
        Map<Position, Piece> pieces = board.getPieces();
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%-3d", i);
            for (int j = 1; j <= 9; j++) {
                Piece piece = pieces.get(new Position(i, j));
                System.out.printf("%s%3s%s ", setPrintColorByTeam(piece), piece.getName(), COLOR_CODE_EXIT);
            }
            System.out.println();
        }
    }

    private String setPrintColorByTeam(Piece piece) {
        if (piece.getTeam().equals(Team.BLUE)) {
            return COLOR_CODE_BLUE;
        }
        if (piece.getTeam().equals(Team.RED)) {
            return COLOR_CODE_RED;
        }
        return COLOR_CODE_YELLOW;
    }
}
