package view;

import domain.Position;
import domain.Side;
import domain.piece.Piece;
import java.util.Map;

public class OutputView {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";
    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printBoardStatus(Map<Position, Piece> board) {
        System.out.println();
        System.out.println("    1  2  3  4  5  6  7  8  9");

        for (int y = 1; y <= 10; y++) {
            printRowNumber(y);
            for (int x = 1; x <= 9; x++) {
                Piece piece = board.get(Position.of(x, y));
                printPiece(piece);
            }
            System.out.println(RESET);
        }
    }

    public void printCurrentTurn(Side currentSide) {
        System.out.println();
        if (currentSide == Side.CHO) {
            System.out.println("초의 차례입니다.");
            return;
        }
        System.out.println("한의 차례입니다.");
    }

    private void printRowNumber(int y) {
        if (y < 10) {
            System.out.print(" " + y + "  ");
        } else {
            System.out.print(y + "  ");
        }
    }

    private void printPiece(Piece piece) {
        String color = getColor(piece.getSide());
        System.out.print(color + piece.getName() + " ");
    }

    private String getColor(Side side) {
        if (side == Side.HAN) {
            return RED;
        }
        if (side == Side.CHO) {
            return BLUE;
        }
        return RESET;
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
    }
}
