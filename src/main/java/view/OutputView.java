package view;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.board.Board;
import domain.piece.Piece;
import domain.position.Position;

public class OutputView {
    private static final int WIDTH = 9;
    private static final int HEIGHT = 10;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public void printBoard(Board board) {
        printColumnHeader();
        for (int row = MIN_ROW; row < MAX_ROW; row++) {
            printPieceRow(row, board);
            printVerticalRow();
        }
        printPieceRow(MAX_ROW, board);
        System.out.println();
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printPlayerTurnMessage(String name, String team) {
        System.out.println(name + "(" + team + ")" + "님의 차례입니다.");
    }

    private void printColumnHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("       ");
        for (int x = 0; x < WIDTH; x++) {
            sb.append(x);
            if (x != WIDTH - 1) {
                sb.append(" ---- ");
            }
        }
        System.out.println(sb);
    }

    private void printPieceRow(int row, Board board) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%3d   ", row));
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Piece piece = board.findPiece(new Position(row, column));
            sb.append(formatCell(piece));
            if (column != MAX_COLUMN) {
                sb.append("---");
            }
        }
        System.out.println(sb);
    }

    private void printVerticalRow() {
        StringBuilder sb = new StringBuilder();
        sb.append("       ");
        for (int x = 0; x < WIDTH; x++) {
            sb.append("|");
            if (x != WIDTH - 1) {
                sb.append("      ");
            }
        }
        System.out.println(sb);
    }

    private String formatCell(Piece piece) {
        String teamString = piece.getTeamString();
        String pieceString = piece.getPieceString();

        if (teamString.isBlank()) {
            return String.format("[%2s]", pieceString);
        }

        char teamCode = teamString.charAt(0);
        if (teamCode == 'C') {
            return String.format("[%s%2s%s]", ANSI_BLUE, pieceString, ANSI_RESET);
        }
        if (teamCode == 'H') {
            return String.format("[%s%2s%s]", ANSI_RED, pieceString, ANSI_RESET);
        }
        return String.format("[%2s]", pieceString);
    }
}
