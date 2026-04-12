package janggi.domain.strategy.arrangement;

import janggi.domain.Side;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Sang;

public class SangMaSangMa implements ArrangementStrategy {

    private final Side side;

    public SangMaSangMa(Side side) {
        this.side = side;
    }

    @Override
    public void place(Piece[][] board) {
        int boardMaxLength = board.length;
        int row = calculateRow(boardMaxLength, side);
        board[row][1] = new Sang(side);
        board[row][2] = new Ma(side);
        board[row][6] = new Sang(side);
        board[row][7] = new Ma(side);
    }
}
