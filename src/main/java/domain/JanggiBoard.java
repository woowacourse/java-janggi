package domain;


import java.util.Map;

public class JanggiBoard {
    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLUMNS = 9;

    private final Map<Position, Piece> janggiBoard;

    public JanggiBoard(Map<Position, Piece> janggiBoard) {
        this.janggiBoard = janggiBoard;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int column = 0; column < BOARD_COLUMNS; column++) {
                janggiBoard.put(new Position(row, column), new Blank());
            }
        }
    }
}

