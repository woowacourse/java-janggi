package janggi.model.turn;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.util.Map;

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

        return new ChoTurn(movedBoard);
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public Map<Position, Piece> getBoard() {
        return board.board();
    }

    @Override
    public boolean isChoTurn() {
        return false;
    }
}
