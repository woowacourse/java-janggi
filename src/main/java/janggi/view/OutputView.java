package janggi.view;

import janggi.domain.Team;
import janggi.dto.BoardDto;
import janggi.dto.BoardDto.CoordinateDto;
import janggi.dto.PieceDto;
import java.util.Arrays;
import java.util.Map;

public class OutputView {
    private static final int BOARD_WIDTH = 9;
    private static final int BOARD_HEIGHT = 10;
    private static final int NODE_CHARS = 10;
    private static final String EMPTY_NODE = "[　]";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    public void printStartMessage() {
        System.out.println("게임을 시작하겠습니다.\n");
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printBoard(BoardDto boardDto) {
        String[][] boardView = generateBoardView(boardDto);
        printXCoordinates();
        printRows(boardView);
        System.out.println();
    }

    private void printRows(String[][] boardView) {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            printNodeRow(boardView[y], y);
            printSpacerIfNecessary(y);
        }
    }

    private String[][] generateBoardView(BoardDto boardDto) {
        String[][] boardView = initializeEmptyBoard();
        fillPieces(boardView, boardDto.getPieces());
        return boardView;
    }

    private String[][] initializeEmptyBoard() {
        String[][] boardView = new String[BOARD_HEIGHT][BOARD_WIDTH];
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            Arrays.fill(boardView[y], EMPTY_NODE);
        }
        return boardView;
    }

    private void fillPieces(String[][] boardView, Map<CoordinateDto, PieceDto> pieces) {
        for (Map.Entry<CoordinateDto, PieceDto> entry : pieces.entrySet()) {
            CoordinateDto coord = entry.getKey();
            PieceDto piece = entry.getValue();
            String factionColor = determineFactionColor(piece.team());

            boardView[coord.column()][coord.row()] = String.format("[%s%s%s]", factionColor, piece.name(), ANSI_RESET);
        }
    }

    private String determineFactionColor(Team team) {
        if (team == Team.HAN) {
            return ANSI_RED;
        }
        return ANSI_GREEN;
    }

    private void printXCoordinates() {
        StringBuilder xAxis = new StringBuilder("   ");
        for (int x = 0; x < BOARD_WIDTH; x++) {
            xAxis.append(" ").append((char) ('\uFF10' + x)).append("        ");
        }
        System.out.println(xAxis + "\n");
    }

    private void printNodeRow(String[] rowNodes, int y) {
        String rowContent = String.join("-------", rowNodes);
        System.out.printf("%d  %s%n", y, rowContent);
    }

    private void printSpacerIfNecessary(int y) {
        if (y < BOARD_HEIGHT - 1) {
            printSpacerRows(y);
        }
    }

    private void printSpacerRows(int y) {
        int[] diagonalOffsets = {2, 5, 8};
        for (int offset : diagonalOffsets) {
            printSingleSpacerLine(y, offset);
        }
    }

    private void printSingleSpacerLine(int y, int offset) {
        char[] spacer = createVerticalLines();
        addPalaceDiagonals(spacer, y, offset);
        System.out.println("   " + new String(spacer));
    }

    private char[] createVerticalLines() {
        char[] spacer = new char[BOARD_WIDTH * NODE_CHARS];
        Arrays.fill(spacer, ' ');
        for (int x = 0; x < BOARD_WIDTH; x++) {
            spacer[x * NODE_CHARS + 1] = 'ㅣ';
        }
        return spacer;
    }

    private void addPalaceDiagonals(char[] spacer, int y, int offset) {
        int x3 = 3 * NODE_CHARS + 1;
        int x4 = 4 * NODE_CHARS + 1;
        int x5 = 5 * NODE_CHARS + 1;

        if (y == 0 || y == 7) {
            spacer[x3 + offset] = '\\';
            spacer[x5 - offset] = '/';
        }
        if (y == 1 || y == 8) {
            spacer[x4 - offset] = '/';
            spacer[x4 + offset] = '\\';
        }
    }

    public void printMenu() {
        System.out.println("1. 새 게임 시작");
        System.out.println("2. 게임 종료");
        System.out.print("메뉴를 선택하세요: ");
    }
}
