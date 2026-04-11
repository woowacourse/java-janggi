package janggi.domain.state;

import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.space.Destinations;
import janggi.domain.space.Position;

public class Finished extends GameState {
    private final Side winner;

    public Finished(Board board, Side winner) {
        super(board);
        this.winner = winner;
    }

    @Override
    public GameState move(Position source, Position target) {
        throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
    }

    @Override
    public Destinations selectSource(Position source) {
        throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
    }

    @Override
    public Side getCurrentSide() {
        throw new IllegalStateException("종료된 게임은 턴이 존재하지 않습니다.");
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public Side getWinnerSide() {
        return winner;
    }
}
