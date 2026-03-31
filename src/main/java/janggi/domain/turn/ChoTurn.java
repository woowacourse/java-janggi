package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;

public class ChoTurn extends Turn {
    public ChoTurn(Board board) {
        super(board, Side.CHO);
    }

    @Override
    public PlayerTurn move(Position start, Position end) {
        board.move(start, end, side);
        if (board.isEndGame()) {
            return new Finish(board, side);
        }
        return new HanTurn(board);
    }
}
