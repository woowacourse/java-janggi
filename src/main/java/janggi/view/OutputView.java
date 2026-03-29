package janggi.view;

import static janggi.view.formatter.CampFormatter.CHO_NAME;
import static java.util.stream.Collectors.joining;

import janggi.dto.CampDto;
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

    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printBoard(List<PiecePositionDto> piecePositions) {
        System.out.println(renderBoard(piecePositions));
    }

    private String renderBoard(List<PiecePositionDto> piecePositions) {
        String[][] board = initializeBoard();
        applyPieces(board, piecePositions);
        return TITLE + LINE_SEPARATOR + renderHeader() + LINE_SEPARATOR + renderRows(board);
    }

    private String[][] initializeBoard() {
        String[][] board = new String[ROW_SIZE][COLUMN_SIZE];
        for (String[] row : board) {
            Arrays.fill(row, EMPTY_CELL);
        }
        return board;
    }

    private String renderHeader() {
        return "  " + IntStream.range(0, COLUMN_SIZE)
                .mapToObj(this::fullWidthNumber)
                .collect(joining(" "));
    }

    private void applyPieces(String[][] board, List<PiecePositionDto> piecePositions) {
        for (PiecePositionDto piecePosition : piecePositions) {
            board[piecePosition.row()][piecePosition.col()] = colorize(piecePosition);
        }
    }

    private String renderRows(String[][] board) {
        return IntStream.range(0, ROW_SIZE)
                .mapToObj(row -> renderRow(row, board[row]))
                .collect(joining(LINE_SEPARATOR));
    }

    private String renderRow(int row, String[] cells) {
        return fullWidthNumber(row) + ' ' + String.join(" ", cells);
    }

    private String colorize(PiecePositionDto piecePosition) {
        return colorOf(piecePosition.camp()) + piecePosition.type() + RESET;
    }

    private String colorOf(CampDto campDto) {
        if (isCho(campDto.camp())) {
            return CHO_COLOR;
        }
        return HAN_COLOR;
    }

    private boolean isCho(String camp) {
        return camp.equals(CHO_NAME);
    }

    private String fullWidthNumber(int number) {
        return FULL_WIDTH_NUMBERS[number];
    }
}
