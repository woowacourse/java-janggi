package janggi.model.turn.playing;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.position.absolute.Position;
import janggi.model.turn.GameOver;
import janggi.model.turn.Turn;

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

        if (movedBoard.isWinnerDetermined()) {
            return new GameOver(board);
        }

        return new HanTurn(movedBoard);
    }

    @Override
    public boolean isChoTurn() {
        return true;
    }
}
