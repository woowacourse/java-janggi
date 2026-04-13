package domain.state;

import domain.piece.Team;

public abstract class PlayingState implements GameState {

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Team getWinner() {
        throw new UnsupportedOperationException("게임이 아직 종료되지 않았습니다.");
    }
}
