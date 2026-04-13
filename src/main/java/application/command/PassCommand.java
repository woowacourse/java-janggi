package application.command;

import dao.GameDAO;
import domain.game.Game;

public record PassCommand() implements GameCommand {
    @Override
    public void execute(Game game, GameDAO gameDAO, int gameId) {
        game.passTurn();

        gameDAO.updateGameStatus(gameId, game);
    }
}
