package config;

import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;
import repository.impl.BoardRepositoryImpl;
import repository.impl.GameRoomRepositoryImpl;
import repository.impl.GameStateRepositoryImpl;
import service.GameService;

public class AppConfig {

    private static final String URL = "jdbc:h2:file:./data/testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private final H2ConnectionManager connectionManager = new H2ConnectionManager(URL, USER, PASSWORD);
    private final BoardRepository boardRepository = new BoardRepositoryImpl();
    private final GameRoomRepository gameRoomRepository = new GameRoomRepositoryImpl();
    private final GameStateRepository gameStateRepository = new GameStateRepositoryImpl();

    private final GameService gameService = new GameService(
            boardRepository,
            gameRoomRepository,
            gameStateRepository,
            connectionManager
    );

    public H2ConnectionManager connectionManager() {
        return connectionManager;
    }

    public GameService gameService() {
        return gameService;
    }
}