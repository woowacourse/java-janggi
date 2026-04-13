package view;

import dto.PieceInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class OutputView {

    private static final int ROW_SIZE = 10;
    private static final int COL_SIZE = 9;

    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String EMPTY = " ＋";
    private static final String HEADER = "   0  1  2  3  4  5  6  7  8";

    private OutputView() {

    }

    public static void printBoard(List<PieceInfo> pieces) {
        Map<String, PieceInfo> pieceMap = buildPieceMap(pieces);
        renderBoard(pieceMap);
    }

    public static void printCurrentPlayerTurn(String turn) {
        System.out.println("현재 차례: " + turn);
    }

    public static void printCurrentPlayerPiecesPointSum(int pointSum) {
        System.out.println("현재 기물들의 점수 합: " + pointSum);
    }

    public static void printResult(String result) {
        System.out.println(result);
    }

    public static void printError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public static void printTaskDivider() {
        System.out.println();
    }

    private static Map<String, PieceInfo> buildPieceMap(List<PieceInfo> pieces) {
        return pieces.stream()
                .collect(Collectors.toMap(p -> key(p.row(), p.col()), p -> p));
    }

    private static String key(int row, int col) {
        return row + "," + col;
    }

    private static void renderBoard(Map<String, PieceInfo> pieceMap) {
        System.out.println(HEADER);
        IntStream.range(0, ROW_SIZE)
                .mapToObj(row -> renderRow(row, pieceMap))
                .forEach(System.out::println);
        System.out.println();
    }

    private static String renderRow(int row, Map<String, PieceInfo> pieceMap) {
        String cells = IntStream.range(0, COL_SIZE)
                .mapToObj(col -> renderCell(row, col, pieceMap))
                .collect(Collectors.joining());
        return row + " " + cells;
    }

    private static String renderCell(int row, int col, Map<String, PieceInfo> pieceMap) {
        PieceInfo piece = pieceMap.get(key(row, col));
        if (piece == null) {
            return emptyCell(row, col);
        }
        return colorize(piece);
    }

    private static String emptyCell(int row, int col) {
        if (isPalace(row, col)) {
            return EMPTY;
        }
        return EMPTY;
    }

    private static boolean isPalace(int row, int col) {
        return col >= 3 && col <= 5 && (row <= 2 || row >= 7);
    }

    private static String colorize(PieceInfo piece) {
        if (piece.isGreenTeam()) {
            return GREEN + " " + piece.name() + RESET;
        }

        if (piece.isRedTeam()) {
            return RED + " " + piece.name() + RESET;
        }
        return " " + piece.name();
    }
}
