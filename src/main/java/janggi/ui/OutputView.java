package janggi.ui;

import janggi.domain.status.Team;
import janggi.dto.GameStatusInfo;
import janggi.dto.PieceInfo;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    public static void printWinner(Team winner) {
        System.out.println("승자는 " + winner.getName());
    }

    public static void printGameStatus(GameStatusInfo status) {
        System.out.println();
        status.pieces().stream()
                .map(row -> row.stream()
                        .map(OutputView::formatPiece)
                        .toList())
                .forEach(System.out::println);
        System.out.println();
    }

    private static String formatPiece(PieceInfo piece) {
        if (piece.team() == null) {
            return piece.name();
        }
        if (piece.team() == Team.CHO) {
            return GREEN + piece.name() + RESET;
        }
        return RED + piece.name() + RESET;
    }
}
