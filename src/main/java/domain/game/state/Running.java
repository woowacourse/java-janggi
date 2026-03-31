package domain.game.state;

import domain.game.Game;

public abstract class Running extends GameState {
    public Running(Game game) {
        super(game);
    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
