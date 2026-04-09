package janggi.model.turn.playing;

import janggi.model.ScorePolicy;
import janggi.model.Team;
import janggi.model.board.Board;
import janggi.model.turn.Turn;

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
    public Board board() {
        return board;
    }

    @Override
    public Team getWinner(ScorePolicy scorePolicy) {
        throw new IllegalStateException("아직 게임이 종료되지 않았습니다.");
    }
}
