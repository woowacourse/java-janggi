package domain.state;

import domain.Board;
import domain.Position;
import domain.constant.Country;
import domain.constant.Turn;

public class Playing implements GameState{
    private final Turn turn;

    public Playing(Turn turn) {
        this.turn = turn;
    }

    @Override
    public GameState move(Board board, Position start, Position end) {
        boolean isJangRemoved = board.move(start, end);
        if (isJangRemoved) {
            return new Finished(turn);
        }
        return new Playing(turn.changeTurn());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Country getTurn() {
        return turn.getCountry();
    }

    @Override
    public Country getWinner() {
        throw new IllegalArgumentException("게임이 진행 중 입니다.");
    }
}
