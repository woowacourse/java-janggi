package janggi.domain.state;

import janggi.domain.Score;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.space.Destinations;
import janggi.domain.space.Position;

public abstract class GameState {
    private final Board board;

    public GameState(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public Score calculateScore(Side side) {
        Score baseScore = board.calculateScore(side);
        if (side == Side.HAN) {
            return baseScore.plus(new Score(1.5));
        }
        return baseScore;
    }

    public boolean isPlaying() {
        return getBoard().isPlaying();
    }

    public abstract Destinations selectSource(Position source);

    public abstract GameState move(Position source, Position target);

    public abstract Side getCurrentSide();

    public abstract Side getWinnerSide();
}
