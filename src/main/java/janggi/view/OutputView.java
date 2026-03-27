package janggi.view;

import static java.util.stream.Collectors.joining;

import janggi.domain.piece.Camp;
import janggi.dto.PiecePositionDto;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public final class OutputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;

    private static final String TITLE = LINE_SEPARATOR + "[장기판]";
    private static final String EMPTY_CELL = "．";

    private static final String RESET = "\u001B[0m";
    private static final String CHO_COLOR = "\u001B[32m";
    private static final String HAN_COLOR = "\u001B[31m";

    private static final String[] FULL_WIDTH_NUMBERS = {
            "０", "１", "２", "３", "４", "５", "６", "７", "８", "９"
    };

    private OutputView() {
    }

    public static void printError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public static void printBoard(List<PiecePositionDto> piecePositions) {
        System.out.println(renderBoard(piecePositions));
    }

    private static String renderBoard(List<PiecePositionDto> piecePositions) {
        String[][] board = initializeBoard();
        applyPieces(board, piecePositions);
        return TITLE + LINE_SEPARATOR + renderHeader() + LINE_SEPARATOR + renderRows(board);
    }

    private static String[][] initializeBoard() {
        String[][] board = new String[ROW_SIZE][COLUMN_SIZE];
        for (String[] row : board) {
            Arrays.fill(row, EMPTY_CELL);
        }
        return board;
    }

    private static String renderHeader() {
        return "  " + IntStream.range(0, COLUMN_SIZE)
                .mapToObj(OutputView::fullWidthNumber)
                .collect(joining(" "));
    }

    private static void applyPieces(String[][] board, List<PiecePositionDto> piecePositions) {
        for (PiecePositionDto piecePosition : piecePositions) {
            board[piecePosition.row()][piecePosition.col()] = colorize(piecePosition);
        }
    }

    private static String renderRows(String[][] board) {
        return IntStream.range(0, ROW_SIZE)
                .mapToObj(row -> renderRow(row, board[row]))
                .collect(joining(LINE_SEPARATOR));
    }

    private static String renderRow(int row, String[] cells) {
        return fullWidthNumber(row) + ' ' + String.join(" ", cells);
    }

    private static String colorize(PiecePositionDto piecePosition) {
        return colorOf(piecePosition.camp()) + piecePosition.type() + RESET;
    }

    private static String colorOf(Camp camp) {
        if (camp == Camp.CHO) {
            return CHO_COLOR;
        }
        return HAN_COLOR;
    }

    private static String fullWidthNumber(int number) {
        return FULL_WIDTH_NUMBERS[number];
    }
}
