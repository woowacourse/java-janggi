package domain.game;

import dao.GameDAO;

public interface GameCommand {
    void execute(Game game, GameDAO gameDAO, int gameId);
}
