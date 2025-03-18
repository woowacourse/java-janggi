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
}
