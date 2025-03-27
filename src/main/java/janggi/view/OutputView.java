package janggi.view;

import janggi.piece.Piece;
import janggi.position.Position;
import janggi.view.format.PieceFormat;
import java.util.Arrays;
import java.util.Map;

public class OutputView {
    private static final int BOARD_LOWER_BOUND_X = 1;
    private static final int BOARD_UPPER_BOUND_X = 9;
    private static final int BOARD_LOWER_BOUND_Y = 1;
    private static final int BOARD_UPPER_BOUND_Y = 10;

    private final PieceFormat pieceFormat;

    public OutputView(final PieceFormat pieceFormat) {
        this.pieceFormat = pieceFormat;
    }

    public void printBoard(final Map<Position, Piece> board) {
        final String[][] boardData = createEmptyBoard();
        for (var entry : board.entrySet()) {
            final String message = pieceFormat.getMessage(entry.getValue());
            boardData[entry.getKey().x()][entry.getKey().y()] = message;
        }
        printBoard(boardData);
    }

    private String[][] createEmptyBoard() {
        final String[][] boardData = new String[BOARD_UPPER_BOUND_X + 1][BOARD_UPPER_BOUND_Y + 1];
        for (int x = BOARD_LOWER_BOUND_X; x <= BOARD_UPPER_BOUND_X; ++x) {
            Arrays.fill(boardData[x], ".");
        }
        return boardData;
    }

    private void printBoard(final String[][] boardData) {
        for (int y = BOARD_LOWER_BOUND_Y; y <= BOARD_UPPER_BOUND_Y; ++y) {
            for (int x = BOARD_LOWER_BOUND_X; x <= BOARD_UPPER_BOUND_X; ++x) {
                System.out.printf("%s\t", boardData[x][y]);
            }
            System.out.printf("\t%d%n", y);
        }
        System.out.println();
        System.out.println("1\t2\t3\t4\t5\t6\t7\t8\t9");
    }
}
