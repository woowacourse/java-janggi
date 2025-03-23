package view;

import static constant.JanggiConstant.BOARD_MAX_HEIGHT;
import static constant.JanggiConstant.BOARD_MAX_WIDTH;
import static constant.JanggiConstant.BOARD_MIN_HEIGHT;
import static constant.JanggiConstant.BOARD_MIN_WIDTH;

import coordinate.Coordinate;
import java.util.Map;
import piece.Piece;

public class OutputView {

    private static final String FULL_WIDTH_BAR = "＿";
    private static final String FULL_WIDTH_SPACE = "　";

    public void printBoard(Map<Coordinate, Piece> board) {
        System.out.println("   １　２　３　４　５　６　７　８　９");

        for (int y = BOARD_MIN_HEIGHT; y <= BOARD_MAX_HEIGHT; y++) {
            System.out.printf("%2d ", y);

            for (int x = BOARD_MIN_WIDTH; x <= BOARD_MAX_WIDTH; x++) {
                Coordinate coordinate = new Coordinate(x, y);
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
