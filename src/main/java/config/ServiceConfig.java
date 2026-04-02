package config;

import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;
import service.GameService;

public class ServiceConfig {
    public GameService gameService(
            BoardRepository boardRepository,
            GameRoomRepository gameRoomRepository,
            GameStateRepository gameStateRepository,
            ConnectionManager connectionManager
    ) {
        return new GameService(
                boardRepository,
                gameRoomRepository,
                gameStateRepository,
                connectionManager
        );
    }
}