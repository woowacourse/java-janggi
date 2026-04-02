package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;

public class HanTurn extends BaseTurn {
    public HanTurn(Board board, int turn) {
        super(board, turn);
    }

    @Override
    public PlayerTurn move(Position start, Position end) {
        board.move(start, end, Side.HAN);

        if(turn == MAX_TURN) {
            return new FinishTurn(board, Side.EMPTY, turn);
        }

        if (board.isEndGame()) {
            return new FinishTurn(board, Side.HAN, turn);
        }
        return new ChoTurn(board, turn + 1);
    }

    @Override
    public Side getCurrentSide() {
        return Side.HAN;
    }
}
