package domain.state;

import domain.Board;
import domain.Position;
import domain.constant.Country;
import domain.constant.Turn;

public class Finished implements GameState {
    private final Turn winner;

    public Finished(Turn winner) {
        this.winner = winner;
    }

    @Override
    public GameState move(Board board, Position start, Position end) {
        throw new IllegalArgumentException("게임이 종료되었습니다.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Country getTurn() {
        throw new IllegalArgumentException("게임이 종료되었습니다.");
    }

    @Override
    public Country getWinner() {
        return winner.getCountry();
    }
}
