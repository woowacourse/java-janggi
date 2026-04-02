package view;

import domain.Position;
import domain.Side;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class OutputView {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";
    private static final String EMPTY = "\uFF0B";
    private static final String SPACE = "\u3000";
    private static final List<String> NUMBERS = List.of(
            "\uFF10", "\uFF11", "\uFF12", "\uFF13", "\uFF14",
            "\uFF15", "\uFF16", "\uFF17", "\uFF18", "\uFF19"
    );

    public void printBoard(Map<Position, Piece> board) {
        System.out.println();
        for (int y = 9; y >= 0; y--) {
            System.out.println(buildRow(board, y));
        }
        System.out.println(buildHeader());
    }

    private String buildHeader() {
        return SPACE.repeat(3) + String.join(SPACE, IntStream.range(0, 9)
                .mapToObj(NUMBERS::get)
                .toList()) + SPACE;
    }

    private String buildRow(Map<Position, Piece> board, int y) {
        return SPACE + NUMBERS.get(y) + SPACE + IntStream.rangeClosed(0, 8)
                .mapToObj(x -> formatCell(board.get(Position.of(x, y))))
                .reduce("", String::concat);
    }

    private String formatCell(Piece piece) {
        if (piece == null) {
            return EMPTY + SPACE;
        }
        return getColor(piece.getSide()) + piece.getName() + SPACE + RESET;
    }

    private String getColor(Side side) {
        if (side == Side.HAN) {
            return RED;
        }
        return BLUE;
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printDestinations(List<Position> destinations) {
        System.out.println(String.join(", ", destinations.stream().map(Position::toString).toList()));
    }

    public void printWinner(String winner) {
        System.out.printf("%s(이/가) 승리했습니다.%n", winner);
    }
}
