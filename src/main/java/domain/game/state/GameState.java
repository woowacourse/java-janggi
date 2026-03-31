package domain.game.state;

import domain.game.Game;
import domain.position.Position;

public abstract class GameState {

    protected Game game;

    public GameState(Game game) {
        this.game = game;
    }

    public abstract void move(Position source, Position destination);
}
