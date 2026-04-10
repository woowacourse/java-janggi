package application;

import domain.game.Game;

public class GameController {
    private final GameStarter gameStarter;
    private final GameSession gameSession;

    public GameController(GameStarter gameStarter, GameSession gameSession) {
        this.gameStarter = gameStarter;
        this.gameSession = gameSession;
    }

    public void run() {
        Game game = gameStarter.start();
        gameSession.run(game);
    }
}
