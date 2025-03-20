package view;

import chessPiece.BoardPosition;
import chessPiece.ChessPiece;
import chessPiece.Nation;
import java.util.Map;

public class OutputView {

    private static final String RED_COLOR_CODE = "\u001B[31m";
    private static final String GREEN_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";
    private static final String EMPTY_SPACE = "ㅤ";
    private static final String[][] JANGGIPAN = new String[11][10];

    public void printJanggipan(Map<BoardPosition, ChessPiece> board) {
        String[][] janggipan = JANGGIPAN;
        createJanggipan(board, janggipan);
        formatJanggipan(janggipan);
    }

    private void createJanggipan(final Map<BoardPosition, ChessPiece> board, final String[][] janggipan) {
        for (BoardPosition boardPosition : board.keySet()) {
            int x = boardPosition.getRow() + 1;
            int y = boardPosition.getCol() + 1;

            ChessPiece chessPiece = board.get(boardPosition);
            String name = chessPiece.getName();
            if (chessPiece.getPieceProfile().getNation().equals(Nation.HAN)) {
                name = RED_COLOR_CODE + chessPiece.getName() + EXIT_CODE;
            }

            if (chessPiece.getPieceProfile().getNation().equals(Nation.CHO)) {
                name = GREEN_COLOR_CODE + chessPiece.getName() + EXIT_CODE;
            }
            janggipan[x][y] = " | " + name;
        }
    }

    private void formatJanggipan(final String[][] janggipan) {
        for (int i = 1; i < janggipan.length; i++) {
            for (int j = 1; j < janggipan[i].length; j++) {
                if (janggipan[i][j] == null) {
                    janggipan[i][j] = " | " + EMPTY_SPACE;
                }
            }
        }

        for (int i = 1; i < 10; i++) {
            janggipan[0][0] = EMPTY_SPACE + EMPTY_SPACE;
            janggipan[0][i] = EMPTY_SPACE + " " + (i - 1) + EMPTY_SPACE;
        }

        for (int i = 1; i <= 10; i++) {
            janggipan[i][0] = i - 1 + EMPTY_SPACE;
        }

        for (int i = 0; i < janggipan.length; i++) {
            for (int j = 0; j < janggipan[i].length; j++) {
                System.out.print(janggipan[i][j]);
            }
            System.out.println();
        }
    }

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }
}
