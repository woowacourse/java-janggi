package janggi.service;

import janggi.domain.JanggiGame;
import janggi.domain.Team;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.PieceSetup;
import janggi.domain.position.Movement;
import janggi.dto.GameInfo;
import janggi.repository.GameRepository;

import java.util.List;

public class JanggiService {

    private final GameRepository repository;

    public JanggiService(GameRepository repository) {
        this.repository = repository;
    }

    public long createGame(PieceSetup hanSetup, PieceSetup choSetup) {
        JanggiGame game = new JanggiGame(BoardFactory.create(hanSetup, choSetup), Team.FIRST_TURN);
        return repository.createGame(game);
    }

    public JanggiGame loadGame(long gameId) {
        return repository.getById(gameId);
    }

    public JanggiGame playTurn(long gameId, Movement movement) {
        JanggiGame game = loadGame(gameId);
        game.play(movement);

        repository.saveGameState(gameId, game);

        return game;
    }

    public List<GameInfo> findAllGames() {
        return repository.findAllGames();
    }

    public void deleteGame(long gameId) {
        repository.deleteGame(gameId);
    }
}
