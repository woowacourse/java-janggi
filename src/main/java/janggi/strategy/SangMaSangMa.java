package janggi.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Sang;

public class SangMaSangMa extends ArrangementStrategy {

    public SangMaSangMa() {
        super(StrategyLabel.EHEH);
    }

    @Override
    public void place(Piece[][] board, Side side) {
        int boardMaxLength = board.length;
        int row = calculateRow(boardMaxLength, side);
        board[row][1] = new Sang(side);
        board[row][2] = new Ma(side);
        board[row][6] = new Sang(side);
        board[row][7] = new Ma(side);
    }
}
