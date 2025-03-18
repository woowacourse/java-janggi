package domain;

import com.google.common.collect.Table;

public class Board {

    private final Table<Integer, Integer, Piece> board;

    public Board() {
        this.board = BoardFactory.createBoard();
    }

    public Table<Integer, Integer, Piece> getBoard() {
        return board;
    }

    public Piece getPieceFrom(int row, int column) {
        return board.get(row, column);
    }

    public void move(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        if (afterRow < 0 || afterRow > 9 || afterColumn < 1 || afterColumn > 9) {
            throw new IllegalArgumentException();
        }
    }
}
