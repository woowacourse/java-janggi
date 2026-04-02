package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;

public class ChoTurn extends BaseTurn {
    public ChoTurn(Board board, int turn) {
        super(board, turn);
    }

    @Override
    public PlayerTurn move(Position start, Position end) {
        board.move(start, end, Side.CHO);

        if(turn == MAX_TURN) {
            return new FinishTurn(board, Side.EMPTY, turn);
        }

        if (board.isEndGame()) {
            return new FinishTurn(board, Side.CHO, turn);
        }

        return new HanTurn(board, turn + 1);
    }

    @Override
    public Side getCurrentSide() {
        return Side.CHO;
    }
}
