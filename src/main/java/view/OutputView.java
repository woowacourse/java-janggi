package view;

import domain.piece.Piece;
import domain.Position;
import domain.Side;
import java.util.List;
import java.util.Map;

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
        System.out.print(SPACE.repeat(3));
        NUMBERS.stream().filter(number -> !number.equals("\uFF19")).forEach(number -> System.out.print(number + SPACE));
        System.out.println();

        for (int y = 9; y >= 0; y--) {
            System.out.print(SPACE + NUMBERS.get(y) + SPACE);
            for (int x = 0; x <= 8; x++) {
                Position position = Position.of(x, y);
                if (board.containsKey(position)) {
                    printPiece(board.get(position));
                    System.out.print(RESET);
                    continue;
                }
                System.out.printf(EMPTY + SPACE);
            }
            System.out.println();
        }
    }

    private void printPiece(Piece piece) {
        String color = getColor(piece.getSide());
        System.out.printf(color + piece + SPACE);
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
        System.out.println(destinations);
    }

    public void printWinner(String winner) {
        System.out.printf("%s가 승리했습니다.%n", winner);
    }
}
