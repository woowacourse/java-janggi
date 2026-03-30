package janggi.model.turn;

import janggi.model.board.Board;
import janggi.model.Team;
import janggi.model.gimul.Piece;
import janggi.model.board.position.Position;
import java.util.Map;

public class ChoTurn implements Turn {

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

        if (movedBoard.isGameOver()) {
            return new GameOver();
        }

        return new HanTurn(movedBoard);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    @Override
    public boolean isChoTurn() {
        return true;
    }
}
