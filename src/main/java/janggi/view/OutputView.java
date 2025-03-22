package janggi.view;

import java.util.Map;
import janggi.piece.ChessPiece;
import janggi.position.BoardPosition;

public class OutputView {

    private static final String RED_COLOR_CODE = "\u001B[31m";
    private static final String GREEN_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";

    private static final String EMPTY_SPACE = "ㅤ";

    private static final int BOARD_WIDTH = 9;
    private static final int BOARD_HEIGHT = 10;
    private static final String[][] JANGGI_BOARD_ARR = new String[BOARD_HEIGHT + 1][BOARD_WIDTH + 1];

    public void printJanggiBoard(Map<BoardPosition, ChessPiece> janggiBoard) {
        initializeJanggiBoard(janggiBoard);
        printFormattedJanggiBoard();
    }

    private void initializeJanggiBoard(final Map<BoardPosition, ChessPiece> janggiBoard) {
        setBoardWithEmptySpaces();
        placeChessPieces(janggiBoard);
        setBoardLabels();
    }

    private void setBoardWithEmptySpaces() {
        for (int i = 1; i <= BOARD_HEIGHT; i++) {
            for (int j = 1; j <= BOARD_WIDTH; j++) {
                JANGGI_BOARD_ARR[i][j] = " | " + EMPTY_SPACE;
            }
        }
    }

    private void placeChessPieces(final Map<BoardPosition, ChessPiece> janggiBoard) {
        for (BoardPosition boardPosition : janggiBoard.keySet()) {
            int row = boardPosition.getRow() + 1;
            int col = boardPosition.getCol() + 1;

            String pieceName = getColoredPieceName(janggiBoard.get(boardPosition));
            JANGGI_BOARD_ARR[row][col] = " | " + pieceName;
        }
    }

    private String getColoredPieceName(final ChessPiece chessPiece) {
        String name = chessPiece.getName();
        if (chessPiece.isChoNation()) {
            return GREEN_COLOR_CODE + name + EXIT_CODE;
        }
        if (chessPiece.isHanNation()) {
            return RED_COLOR_CODE + name + EXIT_CODE;
        }
        return name;
    }

    private void setBoardLabels() {
        JANGGI_BOARD_ARR[0][0] = EMPTY_SPACE + EMPTY_SPACE;

        for (int row = 1; row <= BOARD_HEIGHT; row++) {
            JANGGI_BOARD_ARR[row][0] = (row - 1) + EMPTY_SPACE;
        }

        for (int col = 1; col <= BOARD_WIDTH; col++) {
            JANGGI_BOARD_ARR[0][col] = EMPTY_SPACE + " " + (col - 1) + EMPTY_SPACE;
        }
    }

    private void printFormattedJanggiBoard() {
        System.out.println();
        for (final String[] row : OutputView.JANGGI_BOARD_ARR) {
            for (final String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }
}
