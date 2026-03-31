package domain.game.state;

import domain.game.Game;
import domain.player.Team;
import domain.position.Position;

public abstract class GameState {

    protected Game game;

    public GameState(Game game) {
        this.game = game;
    }

    public abstract void move(Position source, Position destination);

    public abstract boolean isRunning();

    public abstract Team getCurrentTeam();

    public abstract Team getWinner();
}
