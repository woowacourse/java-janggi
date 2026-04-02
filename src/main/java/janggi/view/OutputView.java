package janggi.view;

import janggi.dto.BoardDto;
import janggi.dto.PieceDto;

import java.util.Arrays;
import java.util.List;

public class OutputView {

    private static final int BOARD_WIDTH = 9;
    private static final int BOARD_HEIGHT = 10;
    private static final int NODE_CHARS = 10;
    private static final int SPACER_LINES = 3;
    private static final String EMPTY_NODE = "[　]";

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static final String TEAM_HAN = "HAN";

    public void printStartMessage() {
        System.out.println("게임을 시작하겠습니다.");
    }

    public void printBusinessErrorMessage(String errorMessage) {
        System.out.println("[ERROR] " + errorMessage);
        System.out.println();
    }

    public void printInputErrorMessage(String errorMessage) {
        System.out.println("[WARNING] " + errorMessage);
        System.out.println();
    }

    public void printUndefinedErrorMessage() {
        System.out.println("[FATAL] 예상치 못한 시스템 오류가 발생했습니다.");
        System.out.println();
    }

    public void printBoard(BoardDto boardDto) {
        String[][] boardView = generateBoardView(boardDto);

        printXCoordinates();

        for (int row = 0; row < BOARD_HEIGHT; row++) {
            printNodeRow(boardView, row);
            if (row < BOARD_HEIGHT - 1) {
                printSpacerRows(row);
            }
        }
        System.out.println();
    }

    private String[][] generateBoardView(BoardDto boardDto) {
        String[][] boardView = new String[BOARD_HEIGHT][BOARD_WIDTH];

        for (int row = 0; row < BOARD_HEIGHT; row++) {
            Arrays.fill(boardView[row], EMPTY_NODE);
        }

        List<PieceDto> pieces = boardDto.getPieces();
        for (PieceDto piece : pieces) {
            int row = piece.getRow();
            int col = piece.getColumn();

            String colorCode = determineFactionColor(piece);
            String pieceName = piece.getName();

            String formattedPiece = String.format("[%s%s%s]", colorCode, pieceName, ANSI_RESET);
            boardView[row][col] = formattedPiece;
        }

        return boardView;
    }

    private String determineFactionColor(PieceDto piece) {
        if (TEAM_HAN.equals(piece.getTeam())) {
            return ANSI_RED;
        }
        return ANSI_GREEN;
    }

    private void printXCoordinates() {
        StringBuilder xAxis = new StringBuilder("   ");
        for (int col = 0; col < BOARD_WIDTH; col++) {
            char fullWidthNum = (char) ('\uFF10' + col);
            xAxis.append(" ").append(fullWidthNum).append("        ");
        }
        System.out.println(xAxis.toString());
        System.out.println();
    }

    private void printNodeRow(String[][] boardView, int row) {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append(row).append("  ");

        for (int col = 0; col < BOARD_WIDTH; col++) {
            rowBuilder.append(boardView[row][col]);
            if (col < BOARD_WIDTH - 1) {
                rowBuilder.append("-------");
            }
        }
        System.out.println(rowBuilder.toString());
    }

    private void printSpacerRows(int row) {
        int[] diagonalOffsets = {2, 5, 8};

        for (int spacerIndex = 0; spacerIndex < SPACER_LINES; spacerIndex++) {
            char[] spacer = new char[BOARD_WIDTH * NODE_CHARS];
            Arrays.fill(spacer, ' ');

            for (int col = 0; col < BOARD_WIDTH; col++) {
                spacer[col * NODE_CHARS + 1] = 'ㅣ';
            }

            int offset = diagonalOffsets[spacerIndex];
            addPalaceDiagonals(spacer, row, offset);

            System.out.print("   ");
            System.out.println(new String(spacer));
        }
    }

    private void addPalaceDiagonals(char[] spacer, int row, int offset) {
        int x3Center = 3 * NODE_CHARS + 1;
        int x4Center = 4 * NODE_CHARS + 1;
        int x5Center = 5 * NODE_CHARS + 1;

        if (row == 0 || row == 7) {
            spacer[x3Center + offset] = '\\';
            spacer[x5Center - offset] = '/';
        } else if (row == 1 || row == 8) {
            spacer[x4Center - offset] = '/';
            spacer[x4Center + offset] = '\\';
        }
    }
}
