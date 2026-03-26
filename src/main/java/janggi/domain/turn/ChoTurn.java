package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.board.Board;

public class ChoTurn extends Started {
    public ChoTurn(Board board) {
        super(board);
    }

    @Override
    public PlayerTurn move(Position start, Position end){
        if(board.move(start, end).equals(TurnState.RUNNING)) {
            return new HanTurn(this.board);
        }
        return new Finish(this.board);
    }
}
