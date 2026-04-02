package janggi.model.turn;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import java.util.Map;

public class GameOver implements Turn {

    private final Board board;

    public GameOver(Board board) {
        this.board = board;
    }

    @Override
    public Turn play(Position from, Position to) {
        throw new IllegalStateException("게임 종료 후 턴을 수행할 수 없습니다.");
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

    @Override
    public Map<Position, Piece> getBoard() {
        throw new IllegalStateException("게임이 이미 종료됐습니다.");
    }

    @Override
    public boolean isChoTurn() {
        throw new IllegalStateException("게임이 이미 종료됐습니다.");
    }

    @Override
    public Team getWinner() {
        return board.winner();
    }
}
