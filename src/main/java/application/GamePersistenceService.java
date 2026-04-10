package application;

import domain.GameStatus;
import domain.JanggiGame;
import factory.JanggiBoardFactory;
import persistence.entity.GameState;
import persistence.mapper.GameStateMapper;
import persistence.repository.GameStateRepository;

public final class GamePersistenceService {

    private final GameStateRepository repository;
    private final GameStateMapper gameStateMapper;
    private final JanggiBoardFactory boardFactory;

    public GamePersistenceService(GameStateRepository repository, GameStateMapper gameStateMapper,
                                  JanggiBoardFactory boardFactory) {
        this.repository = repository;
        this.gameStateMapper = gameStateMapper;
        this.boardFactory = boardFactory;
    }

    public JanggiGame loadOrCreate() {
        try {
            GameState gameState = repository.load();
            return gameStateMapper.mapToJanggiGame(gameState);
        } catch (IllegalStateException exception) {
            return JanggiGame.of(boardFactory.initialBoard(), GameStatus.GREEN_PLAYER_TURN);
        }
    }

    public JanggiGame createNewGame() {
        JanggiGame janggiGame = JanggiGame.of(boardFactory.initialBoard(), GameStatus.GREEN_PLAYER_TURN);
        repository.save(gameStateMapper.mapFrom(janggiGame));
        return janggiGame;
    }

    public void save(JanggiGame janggiGame) {
        GameState gameState = gameStateMapper.mapFrom(janggiGame);
        repository.save(gameState);
    }

    public boolean exist() {
        return repository.exist();
    }
}
