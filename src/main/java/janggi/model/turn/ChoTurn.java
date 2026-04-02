package janggi.model.turn;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.absolute.Position;

public class ChoTurn extends PlayingTurn {

    public ChoTurn(Board board) {
        super(board);
    }

    @Override
    public Turn play(Position from, Position to) {
        Board movedBoard = board.move(
                Team.CHO,
                from,
                to
        );

        if (movedBoard.isGameOver()) {
            return new GameOver(board);
        }

        return new HanTurn(movedBoard);
    }

    @Override
    public boolean isChoTurn() {
        return true;
    }
}
