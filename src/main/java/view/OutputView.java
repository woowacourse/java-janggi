package view;

import dto.BoardDto;
import java.util.List;
import view.message.OutputMessage;

public class OutputView {

    private static final String EMPTY_CELL = "    ";

    public static void printRemainScore(List<Double> scores) {
        System.out.println(OutputMessage.GREEN_PIECES_SCORE.getMessage() + scores.getFirst());
        System.out.println(OutputMessage.RED_PIECES_SCORE.getMessage() + scores.getLast());
    }

    public static void printBoard(BoardDto boardDto) {
        List<List<String>> rows = boardDto.convertRows();

        printColumnNumbers();
        printTopBorder();

        for (int row = 0; row < rows.size(); row++) {
            printRow(row, rows.get(row));
            if (isLastRow(rows, row)) {
                continue;
            }
            printMiddleBorder();
        }

        printBottomBorder();
        System.out.println();
    }

    private static void printColumnNumbers() {
        System.out.println("     0    1    2    3    4    5    6    7    8");
    }

    private static void printTopBorder() {
        System.out.println("  ┌────┬────┬────┬────┬────┬────┬────┬────┬────┐");
    }

    private static void printMiddleBorder() {
        System.out.println("  ├────┼────┼────┼────┼────┼────┼────┼────┼────┤");
    }

    private static void printBottomBorder() {
        System.out.println("  └────┴────┴────┴────┴────┴────┴────┴────┴────┘");
    }

    private static void printRow(int rowNumber, List<String> row) {
        StringBuilder builder = new StringBuilder();
        builder.append(rowNumber).append(" │");

        for (String cell : row) {
            builder.append(toCell(cell)).append("│");
        }

        System.out.println(builder);
    }

    private static String toCell(String symbol) {
        if (symbol.isBlank()) {
            return EMPTY_CELL;
        }
        return " " + symbol + " ";
    }

    private static boolean isLastRow(List<List<String>> rows, int row) {
        return row == rows.size() - 1;
    }

    public static void printCurrentPlayerTurn(String playerTurn) {
        System.out.printf(OutputMessage.PLAYER_TURN_SIGN.getMessage(), playerTurn);
        System.out.println();
    }

    public static void printGameResult(String gameResult) {
        System.out.println(gameResult);
    }
}
