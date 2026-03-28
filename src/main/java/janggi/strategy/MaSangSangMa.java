package janggi.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Sang;

@SuppressWarnings("java:S6548")
public class MaSangSangMa extends ArrangementStrategy {

    private static final MaSangSangMa INSTANCE = new MaSangSangMa();

    private MaSangSangMa() {
        super(StrategyLabel.MSSM);
    }

    public static MaSangSangMa getInstance() {
        return INSTANCE;
    }

    @Override
    protected void placeVariablePieces(Piece[][] board, Side side) {
        int boardMaxLength = board.length;
        int row = calculateInitialRow(boardMaxLength, side);
        board[row][1] = new Ma(side);
        board[row][2] = new Sang(side);
        board[row][6] = new Sang(side);
        board[row][7] = new Ma(side);
    }
}
