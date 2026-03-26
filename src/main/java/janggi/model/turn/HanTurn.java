package janggi.model.turn;

import janggi.model.Board;
import janggi.model.Team;
import janggi.model.position.Position;

public class HanTurn implements Turn {

    private final Board board;

    public HanTurn(Board board) {
        this.board = board;
    }

    @Override
    public Turn play(Position from, Position to) {
        Board movedBoard = board.move(
                Team.HAN,
                from,
                to
        );

        if (movedBoard.isGameOver()) {
            return new GameOver();
        }

        return new ChoTurn(board);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
