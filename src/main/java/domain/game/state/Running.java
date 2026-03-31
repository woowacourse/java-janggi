package domain.game.state;

import common.exception.JanggiException;
import domain.game.Game;
import domain.player.Team;

public abstract class Running extends GameState {
    public Running(Game game) {
        super(game);
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public Team getWinner() {
        throw new JanggiException("게임이 아직 종료되지 않았습니다.");
    }
}
