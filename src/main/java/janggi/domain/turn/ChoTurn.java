package janggi.domain.turn;

import janggi.domain.Side;
import janggi.domain.board.Board;

public class ChoTurn extends BaseTurn {
    public ChoTurn(Board board, int turn) {
        super(board, turn);
    }

    @Override
    public Side getCurrentSide() {
        return Side.CHO;
    }

    @Override
    protected PlayerTurn nextTurn() {
        return new HanTurn(board, turn + 1);
    }
}
