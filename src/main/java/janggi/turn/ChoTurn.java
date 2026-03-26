package janggi.turn;

import janggi.Board;
import janggi.Team;
import janggi.position.Position;

public class ChoTurn implements Turn{

    private final Board board;

    public ChoTurn(Board board) {
        this.board = board;
    }

    @Override
    public Turn play(Position from, Position to) {
        Board movedBoard = board.move(
                Team.CHO,
                from,
                to
        );

        return new HanTurn(movedBoard);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
