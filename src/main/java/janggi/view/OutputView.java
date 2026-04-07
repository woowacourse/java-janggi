package janggi.view;

import janggi.domain.PieceInfo;
import janggi.domain.ScoreStatus;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;

public class OutputView {
    private static final String SCORE_FORMAT = "한 점수: %.1f, 초 점수: %.0f%n";
    private static final int CELL_WIDTH = 5;
    private static final String EMPTY_CELL = ".";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private static final String TURN_PREFIX = "현재 턴: ";
    private static final String CREATE_NEW_GAME_ROOM_FORMAT = "새 게임방을 생성했습니다. %d번방\n";
    private static final String GAME_ROOM_NUMBER_FORMAT = "%d번방\n";
    private static final String ERROR_PREFIX = "[ERROR] ";

    public static void printBoard(PieceInfo[][] currentBoard) {
        StringBuilder result = new StringBuilder();
        result.append(buildColumnHeader(currentBoard[0].length));
        appendBoardRows(currentBoard, result);
        System.out.print(result);
    }

    public static void printTurn(Side side) {
        System.out.println(TURN_PREFIX + side.getName());
    }

    public static void printScoreStatus(ScoreStatus scoreStatus) {
        System.out.printf(SCORE_FORMAT, scoreStatus.hanScore(), scoreStatus.choScore());
    }

    public static void printWinner(Side winnerSide) {
        System.out.printf("%s 승리!%n", winnerSide.getName());
    }

    public static void printNewGameRoom(long roomId) {
        System.out.printf(CREATE_NEW_GAME_ROOM_FORMAT, roomId);
    }

    public static void printGameRoomNumber(long roomId) {
        System.out.printf(GAME_ROOM_NUMBER_FORMAT, roomId);
    }

    public static void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
    }

    private static String buildColumnHeader(int colSize) {
        StringBuilder header = new StringBuilder("     ");
        for (int col = 1; col <= colSize; col++) {
            header.append(String.format(" %-4d ", col));
        }
        header.append(System.lineSeparator());
        return header.toString();
    }

    private static void appendBoardRows(PieceInfo[][] board, StringBuilder result) {
        result.append(buildTopBorder(board[0].length));
        for (int row = 0; row < board.length; row++) {
            appendBoardRow(board[row], row, result);
            result.append(buildDivider(board[row].length, row == board.length - 1));
        }
    }

    private static void appendBoardRow(PieceInfo[] row, int rowIndex, StringBuilder result) {
        result.append(String.format(" %2d ┃", rowIndex + 1));
        for (PieceInfo pieceInfo : row) {
            result.append(formatCell(pieceInfo));
            result.append("┃");
        }
        result.append(System.lineSeparator());
    }

    private static String buildTopBorder(int colSize) {
        return buildBorder("┏", "┳", "┓", colSize);
    }

    private static String buildDivider(int colSize, boolean isLastRow) {
        if (isLastRow) {
            return buildBorder("┗", "┻", "┛", colSize);
        }
        return buildBorder("┣", "╋", "┫", colSize);
    }

    private static String buildBorder(String start, String middle, String end, int colSize) {
        StringBuilder border = new StringBuilder("    ").append(start);
        for (int col = 0; col < colSize; col++) {
            border.append("━".repeat(CELL_WIDTH));
            if (col < colSize - 1) {
                border.append(middle);
            }
        }
        border.append(end);
        border.append(System.lineSeparator());
        return border.toString();
    }

    private static String formatCell(PieceInfo pieceInfo) {
        if (isEmpty(pieceInfo)) {
            return "  " + EMPTY_CELL + "  ";
        }
        return " " + colorize(pieceInfo.pieceType().getName(), pieceInfo.side()) + "  ";
    }

    private static String colorize(String pieceName, Side side) {
        if (side == Side.HAN) {
            return ANSI_RED + pieceName + ANSI_RESET;
        }
        if (side == Side.CHO) {
            return ANSI_GREEN + pieceName + ANSI_RESET;
        }
        return pieceName;
    }

    private static boolean isEmpty(PieceInfo pieceInfo) {
        if (pieceInfo == null) {
            return true;
        }
        return pieceInfo.pieceType() == PieceType.NONE || pieceInfo.side() == Side.EMPTY;
    }
}
