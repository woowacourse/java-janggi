package janggi.model.turn;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.absolute.Position;

public class HanTurn extends PlayingTurn {

    public HanTurn(Board board) {
        super(board);
    }

    @Override
    public Turn play(Position from, Position to) {
        Board movedBoard = board.move(
                Team.HAN,
                from,
                to
        );

        if (movedBoard.isGameOver()) {
            return new GameOver(board);
        }

        return new ChoTurn(movedBoard);
    }

    @Override
    public boolean isChoTurn() {
        return false;
    }
}
