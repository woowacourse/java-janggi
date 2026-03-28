package janggi.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Sang;

@SuppressWarnings("java:S6548")
public class SangMaSangMa extends ArrangementStrategy {

    private static final SangMaSangMa INSTANCE = new SangMaSangMa();

    private SangMaSangMa() {
        super(StrategyLabel.EHEH);
    }

    public static SangMaSangMa getInstance() {
        return INSTANCE;
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
