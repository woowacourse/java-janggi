package janggi.view;

import static java.util.stream.Collectors.joining;

import janggi.domain.piece.camp.CampType;
import janggi.dto.PiecePositionDto;
import janggi.view.format.CampFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class OutputView {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final int ROW_SIZE = 10;
    private static final int COLUMN_SIZE = 9;

    private static final String EMPTY_CELL = "．";

    private static final String RESET_COLOR = "\u001B[0m";

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
        return LINE_SEPARATOR + "[장기판]" + LINE_SEPARATOR + renderHeader() + LINE_SEPARATOR + renderRows(board);
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
            board[piecePosition.row()][piecePosition.column()] = colorize(piecePosition);
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
        CampFormat campFormat = CampFormat.from(piecePosition.campType());
        return campFormat.getColor() + piecePosition.type() + RESET_COLOR;
    }

    private static String fullWidthNumber(int number) {
        return FULL_WIDTH_NUMBERS[number];
    }

    public static void printScore(Map<CampType, Double> scoreBoard) {
        System.out.println(LINE_SEPARATOR + "" +
                "현재 점수");
        for (Map.Entry<CampType, Double> entry : scoreBoard.entrySet()) {
            System.out.println(CampFormat.from(entry.getKey()).getName() + "나라: " + entry.getValue());
        }
    }

    public static void printWinner(CampType campType) {
        System.out.println(CampFormat.from(campType).getName() + "나라 승리!");
    }
}
