package janggi.view;

import janggi.domain.Side;
import janggi.domain.SideScore;
import janggi.domain.piece.PieceAttribute;
import janggi.domain.piece.PieceType;
import janggi.dto.BoardDto;
import java.util.List;

public class OutputView {
    private static final int CELL_WIDTH = 5;
    private static final String EMPTY_CELL = ".";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private static final String TURN_PREFIX = "현재 턴: ";

    private static final String SCORE_PREFIX = "[ 현재 점수 현황 ]";
    private static final String HAN_SCORE_PREFIX = "한: ";
    private static final String CHO_SCORE_PREFIX = "초: ";
    private static final String SCORE_SUFFIX = "점";
    private static final String SCORE_DELIMITER = ", ";

    private static final String ERROR_PREFIX = "[ERROR] ";

    public static void printLine() {
        System.out.println();
    }

    public static void printBoard(BoardDto boardDto) {
        List<List<PieceAttribute>> board = boardDto.board();
        StringBuilder result = new StringBuilder();
        result.append(buildColumnHeader(board.getFirst().size()));
        appendBoardRows(board, result);
        System.out.print(result);
    }

    public static void printTurn(Side side) {
        System.out.println(TURN_PREFIX + side.getName());
    }

    public static void printScore(SideScore score) {
        System.out.println(SCORE_PREFIX);
        System.out.println(CHO_SCORE_PREFIX + score.cho() + SCORE_SUFFIX + SCORE_DELIMITER + HAN_SCORE_PREFIX + score.han() + SCORE_SUFFIX);
    }

    public static void printWinner(Side winnerSide) {
        System.out.printf("%s 승리!%n", winnerSide.getName());
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

    private static void appendBoardRows(List<List<PieceAttribute>> board, StringBuilder result) {
        result.append(buildTopBorder(board.getFirst().size()));
        for (int row = 0; row < board.size(); row++) {
            appendBoardRow(board.get(row), row, result);
            result.append(buildDivider(board.get(row).size(), row == board.size() - 1));
        }
    }

    private static void appendBoardRow(List<PieceAttribute> row, int rowIndex, StringBuilder result) {
        result.append(String.format(" %2d ┃", rowIndex + 1));
        for (PieceAttribute pieceAttribute : row) {
            result.append(formatCell(pieceAttribute));
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

    private static String formatCell(PieceAttribute pieceAttribute) {
        if (isEmpty(pieceAttribute)) {
            return "  " + EMPTY_CELL + "  ";
        }
        return " " + colorize(pieceAttribute.pieceType().getName(), pieceAttribute.side()) + "  ";
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

    private static boolean isEmpty(PieceAttribute pieceAttribute) {
        if (pieceAttribute == null) {
            return true;
        }
        return pieceAttribute.pieceType() == PieceType.NONE || pieceAttribute.side() == Side.EMPTY;
    }
}
