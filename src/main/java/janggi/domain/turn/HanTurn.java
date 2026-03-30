package janggi.domain.turn;

import janggi.domain.MoveResult;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;

public class HanTurn extends Started {
    public HanTurn(Board board) {
        super(board, Side.HAN);
    }

    @Override
    public PlayerTurn move(Position start, Position end) {
        MoveResult moveResult = board.move(start, end, side);
        if (moveResult.isCapturedGung()) {
            return new Finish(board, side);
        }
        return new ChoTurn(board);
    }
}
