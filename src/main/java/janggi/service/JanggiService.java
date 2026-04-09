package janggi.service;

import janggi.JanggiGame;
import janggi.JanggiGame2;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.PieceSetup;
import janggi.domain.position.Movement;
import janggi.domain.team.Team;
import janggi.repository.GameDao;
import janggi.repository.GameInfo2;
import janggi.repository.GameRepository;

import java.util.List;

public class JanggiService {

    private final GameDao gameDao;

    public JanggiService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public long createGame(PieceSetup hanSetup, PieceSetup choSetup) {
        JanggiGame2 game = new JanggiGame2(BoardFactory.create(hanSetup, choSetup), Team.FIRST_TURN);
        return gameDao.createGame(game);
    }

    public JanggiGame2 loadGame(long gameId) {
        return gameDao.getById(gameId);
    }

    public JanggiGame2 playTurn(long gameId, Movement movement) {
        JanggiGame2 game = loadGame(gameId);
        game.play(movement);

        gameDao.updateGame(gameId, game);

        return game;
    }

    public List<GameInfo2> findAllGames() {
        return gameDao.findAllGames();
    }
}
