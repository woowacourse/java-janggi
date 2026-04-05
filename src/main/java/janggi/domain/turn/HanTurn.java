package janggi.domain.turn;

import janggi.domain.Side;
import janggi.domain.board.Board;

public class HanTurn extends BaseTurn {

    public HanTurn(Board board, int turn) {
        super(board, turn);
    }

    @Override
    public Side getCurrentSide() {
        return Side.HAN;
    }

    @Override
    protected PlayerTurn nextTurn() {
        return new ChoTurn(board, turn + 1);
    }
}
