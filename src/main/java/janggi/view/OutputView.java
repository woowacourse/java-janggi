package janggi.view;

import janggi.dto.BoardDto;

import janggi.dto.BoardDto.CoordinateDto;
import java.util.Arrays;
import java.util.Map;

public class OutputView {

    private static final int BOARD_WIDTH = 9;
    private static final int BOARD_HEIGHT = 10;
    private static final int NODE_CHARS = 10;
    private static final int SPACER_LINES = 3;
    private static final String EMPTY_NODE = "[　]";

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    public void printStartMessage() {
        System.out.println("게임을 시작하겠습니다.");
        System.out.println();
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }
    // 고민) depth 2 -> 1
    public void printBoard(BoardDto boardDto) {
        String[][] boardView = generateBoardView(boardDto);

        printXCoordinates();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            printNodeRow(boardView, y);
            if (y < BOARD_HEIGHT - 1) {
                printSpacerRows(y);
            }
        }
        System.out.println();
    }

    private String[][] generateBoardView(BoardDto boardDto) {
        String[][] boardView = new String[BOARD_HEIGHT][BOARD_WIDTH];

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            Arrays.fill(boardView[y], EMPTY_NODE);
        }

        Map<CoordinateDto, String> pieces = boardDto.getPieces();
        for (Map.Entry<CoordinateDto, String> entry : pieces.entrySet()) {
            CoordinateDto coordinateDto = entry.getKey();
            int x = coordinateDto.row();
            int y = coordinateDto.column();
            String pieceName = entry.getValue();

            String colorCode = determineFactionColor(pieceName, y);

            String formattedPiece = String.format("[%s%s%s]", colorCode, pieceName, ANSI_RESET);
            boardView[y][x] = formattedPiece;
        }

        return boardView;
    }

    private String determineFactionColor(String pieceName, int y) {
        if (pieceName.equals("漢") || pieceName.equals("兵")) {
            return ANSI_RED;
        }
        if (pieceName.equals("楚") || pieceName.equals("卒")) {
            return ANSI_GREEN;
        }

        if (y <= 4) {
            return ANSI_RED;
        }
        return ANSI_GREEN;
    }

    private void printXCoordinates() {
        StringBuilder xAxis = new StringBuilder("   ");
        for (int x = 0; x < BOARD_WIDTH; x++) {
            char fullWidthNum = (char) ('\uFF10' + x);
            xAxis.append(" ").append(fullWidthNum).append("        ");
        }
        System.out.println(xAxis.toString());
        System.out.println();
    }
    // 고민) depth 2 -> 1
    private void printNodeRow(String[][] boardView, int y) {
        StringBuilder row = new StringBuilder();
        row.append(y).append("  ");
        for (int x = 0; x < BOARD_WIDTH; x++) {
            row.append(boardView[y][x]);
            if (x < BOARD_WIDTH - 1) {
                row.append("-------");
            }
        }
        System.out.println(row.toString());
    }
    // 고민) depth 2 -> 1
    private void printSpacerRows(int y) {
        int[] diagonalOffsets = {2, 5, 8};

        for (int spacerIndex = 0; spacerIndex < SPACER_LINES; spacerIndex++) {
            char[] spacer = new char[BOARD_WIDTH * NODE_CHARS];
            Arrays.fill(spacer, ' ');

            for (int x = 0; x < BOARD_WIDTH; x++) {
                spacer[x * NODE_CHARS + 1] = 'ㅣ';
            }

            int offset = diagonalOffsets[spacerIndex];
            addPalaceDiagonals(spacer, y, offset);

            System.out.print("   ");
            System.out.println(new String(spacer));
        }
    }

    private void addPalaceDiagonals(char[] spacer, int y, int offset) {
        int x3Center = 3 * NODE_CHARS + 1;
        int x4Center = 4 * NODE_CHARS + 1;
        int x5Center = 5 * NODE_CHARS + 1;

        if (y == 0 || y == 7) {
            spacer[x3Center + offset] = '\\';
            spacer[x5Center - offset] = '/';
        } else if (y == 1 || y == 8) {
            spacer[x4Center - offset] = '/';
            spacer[x4Center + offset] = '\\';
        }
    }
}
