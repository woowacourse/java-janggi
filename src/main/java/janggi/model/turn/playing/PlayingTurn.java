package janggi.model.turn.playing;

import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Position;
import janggi.model.turn.Turn;
import java.util.Map;

public abstract class PlayingTurn implements Turn {

    protected final Board board;

    protected PlayingTurn(Board board) {
        this.board = board;
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
    public Team getWinner() {
        throw new IllegalStateException("아직 게임이 종료되지 않았습니다.");
    }

    @Override
    public int getTotalScoreOf(Team team) {
        throw new IllegalStateException("아직 게임이 종료되지 않았습니다.");
    }
}
