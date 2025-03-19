package domain;

import com.google.common.collect.Table;

public class JanggiBoard {

    private final Table<Integer, Integer, Piece> janggiBoard;

    public JanggiBoard() {
        this.janggiBoard = JanggiBoardFactory.createJanggiBoard();
    }

    public Table<Integer, Integer, Piece> getJanggiBoard() {
        return janggiBoard;
    }

    public Piece getPieceFrom(int row, int column) {
        return janggiBoard.get(row, column);
    }

    public void move(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        if (afterRow < 0 || afterRow > 9 || afterColumn < 1 || afterColumn > 9) {
            throw new IllegalArgumentException();
        }
        Piece piece = getPieceFrom(beforeRow, beforeColumn);
        boolean isHurdle = isExistHurdle(piece, beforeRow, beforeColumn, afterRow, afterColumn);

        if (piece.getClass().equals(포.class)) {
            move포(beforeRow, beforeColumn, afterRow, afterColumn);
            return;
        }

        int transformedBeforeRow = beforeRow;
        int transformedAfterRow = afterRow;

        if (beforeRow == 0) {
            transformedBeforeRow = 10;
        }
        if (afterRow == 0) {
            transformedAfterRow = 10;
        }

        if (!piece.canMove(transformedBeforeRow, beforeColumn, transformedAfterRow, afterColumn)) {
            throw new IllegalArgumentException();
        }
        janggiBoard.put(beforeRow, beforeColumn, new Empty());
        janggiBoard.put(afterRow, afterColumn, piece);
    }

    private boolean isExistHurdle(Piece piece, int beforeRow, int beforeColumn, int afterRow, int afterColumn) {

    }

    private void move포(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {

    }


}
