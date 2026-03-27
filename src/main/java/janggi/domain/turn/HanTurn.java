package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;

public class HanTurn extends Started {
    public HanTurn(Board board) {
        super(board, Side.EMPTY);
    }

    @Override
    public PlayerTurn move(Position start, Position end){
        if(board.move(start, end)) {
            return new ChoTurn(board);
        }
        return new Finish(board, Side.HAN);
    }
}
