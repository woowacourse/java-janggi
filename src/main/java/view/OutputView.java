package view;

import domain.BoardPosition;
import domain.Piece;
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
            printRowHeader(i);
            for (int j = 0; j < 9; j++) {
                final BoardPosition boardPosition = new BoardPosition(j, i);
                final Piece piece = pieces.get(boardPosition);
                printPiece(piece);
            }
            System.out.println();
        }
        printColumnHeader();
    }

    private void printRowHeader(final int i) {
        System.out.print(i + " ");
    }

    private void printPiece(final Piece piece) {
        if (piece == null) {
            System.out.print(" . ");
            return;
        }
        
        final String pieceTitle = piece.getPieceType()
            .getTitle();
        final String color = createColorCode(piece.getTeam());
        System.out.print(" " + color + pieceTitle + RESET + " ");
    }

    private void printColumnHeader() {
        System.out.print("  ");
        for (int j = 0; j < 9; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();
    }

    private String createColorCode(final Team team) {
        if (team == Team.RED) {
            return RED;
        }

        return GREEN;
    }
}
