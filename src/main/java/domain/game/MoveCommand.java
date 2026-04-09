package domain.game;

import dao.GameDAO;
import domain.board.Position;

public record MoveCommand(Position from, Position to) implements GameCommand {
    @Override
    public void execute(Game game, GameDAO gameDAO, int gameId) {
        game.move(from(), to());
        gameDAO.updateMove(gameId, game, from(), to());
    }
}
