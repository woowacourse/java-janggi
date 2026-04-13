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
        return repository.load()
                .map(gameStateMapper::mapToJanggiGame)
                .orElseGet(this::createNewGame);
    }

    public JanggiGame createNewGame() {
        JanggiGame janggiGame = JanggiGame.of(boardFactory.initialBoard(), GameStatus.GREEN_PLAYER_TURN);
        save(janggiGame);
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
