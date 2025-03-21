package view;

import coordinate.Coordinate;
import java.util.Map;
import piece.Piece;

public class OutputView {

    private static final String FULL_WIDTH_BAR = "＿";
    private static final String FULL_WIDTH_SPACE = "　";

    public void printBoard(Map<Coordinate, Piece> board) {
        System.out.println("   １　２　３　４　５　６　７　８　９");

        for (int height = 1; height <= 10; height++) {
            System.out.printf("%2d ", height);

            for (int width = 1; width <= 9; width++) {
                Coordinate coordinate = new Coordinate(width, height);
                Piece piece = board.get(coordinate);
                if (piece == null) {
                    System.out.print(FULL_WIDTH_BAR + FULL_WIDTH_SPACE);
                    continue;
                }
                System.out.print(piece.colorName() + FULL_WIDTH_SPACE);
            }
            System.out.println();
        }
        System.out.println();
    }
}
