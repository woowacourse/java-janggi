package janggi.turn;

import janggi.Board;
import janggi.position.Position;

public class HanTurn implements Turn{

    private final Board board;

    public HanTurn(Board board) {
        this.board = board;
    }

    @Override
    public Turn play(Position from, Position to) {
        return new ChoTurn(board);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
