package janggi.domain.turn;

import janggi.domain.MoveResult;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;

public class ChoTurn extends Started {
    public ChoTurn(Board board) {
        super(board, Side.CHO);
    }

    @Override
    public PlayerTurn move(Position start, Position end) {
        MoveResult moveResult = board.move(start, end, side);
        if (moveResult.isCapturedGung()) {
            return new Finish(board);
        }
        return new HanTurn(board);
    }
}
