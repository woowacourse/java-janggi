package view;

import domain.Piece;
import domain.vo.Position;

import java.util.Map;

public class OutputView {
    private static final String RESET_COLOR = "\u001B[0m";
    private static final String CHU_COLOR = "\u001B[32m";
    private static final String HAN_COLOR = "\u001B[31m";

    private static final int MAX_COL = 8;
    private static final int MAX_ROW = 9;

    public void printBoard(Map<Position, Piece> board) {
        System.out.println();
        for (int row = MAX_ROW; row >= 0; row--) {
            for (int col = 0; col <= MAX_COL; col++) {
                Position position = Position.of(row, col);
                if (board.containsKey(position)) {
                    Piece piece = board.get(position);
                    String color = getNationColor(piece);
                    System.out.print(color + piece.getTypeName() + RESET_COLOR + "  ");
                } else {
                    System.out.print("x   ");
                }
            }
            System.out.println();
            System.out.println();
        }

        System.out.println();
    }

    private static String getNationColor(Piece piece) {
        return piece.getTeam().name().equals("CHU") ? CHU_COLOR : HAN_COLOR;
    }
}
