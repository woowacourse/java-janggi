package domain.game;

import dao.GameDAO;

public record PassCommand() implements GameCommand {
    @Override
    public void execute(Game game, GameDAO gameDAO, int gameId) {
        game.passTurn();

        gameDAO.updateGameStatus(gameId, game);
    }
}
