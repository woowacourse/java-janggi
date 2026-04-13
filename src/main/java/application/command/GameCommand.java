package application.command;

import dao.GameDAO;
import domain.game.Game;

public interface GameCommand {
    void execute(Game game, GameDAO gameDAO, int gameId);
}
