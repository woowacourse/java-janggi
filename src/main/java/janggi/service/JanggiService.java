package janggi.service;

import janggi.JanggiGame2;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.PieceSetup;
import janggi.domain.position.Movement;
import janggi.domain.team.Team;
import janggi.repository.GameRepository2;
import janggi.repository.GameInfo2;

import java.util.List;

public class JanggiService {

    private final GameRepository2 gameRepository2;

    public JanggiService(GameRepository2 gameRepository2) {
        this.gameRepository2 = gameRepository2;
    }

    public long createGame(PieceSetup hanSetup, PieceSetup choSetup) {
        JanggiGame2 game = new JanggiGame2(BoardFactory.create(hanSetup, choSetup), Team.FIRST_TURN);
        return gameRepository2.createGame(game);
    }

    public JanggiGame2 loadGame(long gameId) {
        return gameRepository2.getById(gameId);
    }

    public JanggiGame2 playTurn(long gameId, Movement movement) {
        JanggiGame2 game = loadGame(gameId);
        game.play(movement);

        gameRepository2.updateGame(gameId, game);

        return game;
    }

    public List<GameInfo2> findAllGames() {
        return gameRepository2.findAllGames();
    }
}
