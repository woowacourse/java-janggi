package view;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;

import domain.piece.Piece;
import domain.player.Team;
import domain.position.Position;
import java.util.Map;

public class OutputView {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public void printBoard(Map<Position, Piece> board) {
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
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append(column);
            if (column != MAX_COLUMN) {
                sb.append(" ---- ");
            }
        }
        System.out.println(sb);
    }

    private void printPieceRow(int row, Map<Position, Piece> board) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%3d   ", row));
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Piece piece = board.get(new Position(row, column));
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
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            sb.append("|");
            if (column != MAX_COLUMN) {
                sb.append("      ");
            }
        }
        System.out.println(sb);
    }

    private String formatCell(Piece piece) {
        String pieceString = piece.getPieceString();
        Team team = piece.getTeam();

        if (team.isNull()) {
            return String.format("[%2s]", pieceString);
        }
        if (team.isCho()) {
            return String.format("[%s%2s%s]", ANSI_BLUE, pieceString, ANSI_RESET);
        }
        if (team.isHan()) {
            return String.format("[%s%2s%s]", ANSI_RED, pieceString, ANSI_RESET);
        }
        return String.format("[%2s]", pieceString);
    }
}
