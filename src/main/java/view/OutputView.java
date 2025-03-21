package view;

import domain.board.BoardPosition;
import domain.piece.Piece;
import domain.Team;
import java.util.Map;

public class OutputView {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    public void printBoard(
            final Map<BoardPosition, Piece> pieces,
            final Team team
    ) {
        System.out.println("현재 턴: " + team.getTitle());
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j < 9; j++) {
                final BoardPosition boardPosition = new BoardPosition(j, i);
                final Piece piece = pieces.get(boardPosition);

                if (piece == null) {
                    System.out.print(" . ");
                } else {
                    final String pieceSymbol = "@";
                    final String color = createColorCode(piece.getTeam());
                    System.out.print(" " + color + pieceSymbol + RESET + " ");
                }
            }
            System.out.println();
        }
    }

    private String createColorCode(final Team team) {
        if (team == Team.RED) {
            return RED;
        }

        return GREEN;
    }
}
