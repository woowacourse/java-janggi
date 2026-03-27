package view;

import java.util.List;

public class OutputView {
    private static final int WIDTH = 9;
    private static final int HEIGHT = 10;

    private static final String ANSI_CLS = "\u001b[2J";
    private static final String ANSI_HOME = "\u001b[H";

    public void printBoard(List<List<String>> board) {
        validateBoard(board);
        System.out.println();
        printColumnHeader();
        for (int y = 0; y < 9; y++) {
            printPieceRow(y, board.get(y));
            printVerticalRow();
        }
        printPieceRow(9, board.get(9));
        System.out.println();
    }

    public void clearScreen() {
        System.out.print(ANSI_HOME + ANSI_CLS);
        System.out.flush();
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printPlayerTurnMessage(String name, String team) {
        System.out.println(name + "(" + team + ")" +"님의 차례입니다.");
    }

    private void printColumnHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("       ");
        for (int x = 1; x <= WIDTH; x++) {
            sb.append(x);
            if (x != WIDTH) {
                sb.append(" ---- ");
            }
        }
        System.out.println(sb);
        System.out.println();
    }

    private void printPieceRow(int y, List<String> row) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%3d   ", y));
        for (int x = 0; x < WIDTH; x++) {
            sb.append(formatCell(row.get(x)));
            if (x != WIDTH - 1) {
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

    private String formatCell(String piece) {
        if (piece == null || piece.isBlank()) {
            return "[  ]";
        }
        return String.format("[%2s]", piece);
    }

    private void validateBoard(List<List<String>> board) {
        if (board == null || board.size() != HEIGHT) {
            throw new IllegalArgumentException("보드는 세로 10줄이어야 합니다.");
        }
        for (List<String> row : board) {
            if (row == null || row.size() != WIDTH) {
                throw new IllegalArgumentException("각 행은 가로 9칸이어야 합니다.");
            }
        }
    }
}
