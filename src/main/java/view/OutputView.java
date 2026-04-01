package view;

import controller.dto.CurrentBoardStatus;
import java.util.List;

public class OutputView {
    private static final int BOARD_ROW_SIZE = 10;
    private static final int BOARD_COLUMN_SIZE = 9;
    private static final String EMPTY_CELL = "   ";

    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";

    public void printCurrentBoard(List<CurrentBoardStatus> statuses) {
        String[][] board = createEmptyBoard();
        placePieces(board, statuses);

        printHeader();
        printSeparator();

        for (int row = 1; row <= BOARD_ROW_SIZE; row++) {
            printRow(board, row);
            printSeparator();
        }
    }

    public void printErrorMessage(String message){
        System.out.println(message);
    }

    /**
     * 헬퍼 메서드
     */

    private String[][] createEmptyBoard() {
        String[][] board = new String[BOARD_ROW_SIZE][BOARD_COLUMN_SIZE];

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int column = 0; column < BOARD_COLUMN_SIZE; column++) {
                board[row][column] = EMPTY_CELL;
            }
        }

        return board;
    }

    private void placePieces(String[][] board, List<CurrentBoardStatus> statuses) {
        for (CurrentBoardStatus status : statuses) {
            int rowIndex = status.row() - 1;
            int colIndex = status.column() - 1;

            board[rowIndex][colIndex] = formatPiece(status.pieceType(), status.team());
        }
    }

    private void printHeader() {
        System.out.println("        1      2      3      4      5      6      7      8      9");
    }

    private void printSeparator() {
        System.out.println("    +------+------+------+------+------+------+------+------+------+");
    }

    private void printRow(String[][] board, int row) {
        System.out.printf("%2d  |", row);

        for (int col = 1; col <= BOARD_COLUMN_SIZE; col++) {
            System.out.printf(" %-4s |", board[row - 1][col - 1]);
        }

        System.out.println();
    }

    private String formatPiece(String pieceType, String team) {
        if ("초".equals(team)) {
            return BLUE + " " + pieceType + " " + RESET;
        }
        if ("한".equals(team)) {
            return RED + " " + pieceType + " " + RESET;
        }
        return pieceType;
    }
}
