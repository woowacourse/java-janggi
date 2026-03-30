package view;

import domain.Piece;
import domain.Position;
import domain.Side;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    public void printBoard(Map<Position, Piece> board) {
        System.out.println();
        System.out.println("    0  1  2  3  4  5  6  7  8");

        for (int y = 9; y >= 0; y--) {
            System.out.print(" " + y + "  ");
            for (int x = 0; x <= 8; x++) {
                Position position = Position.of(x, y);
                if (board.containsKey(position)) {
                    printPiece(board.get(position));
                    System.out.print(RESET);
                    continue;
                }
                System.out.printf("%-3s", "+");
            }
            System.out.println();
        }
    }

    private void printPiece(Piece piece) {
        String color = getColor(piece.getSide());
        System.out.printf(color + "%-3s", piece);
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
