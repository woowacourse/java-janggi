package config;

import infra.ConnectionManager;
import infra.DBExecutor;
import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;
import service.GameService;

public class ServiceConfig {
    public GameService gameService(
            BoardRepository boardRepository,
            GameRoomRepository gameRoomRepository,
            GameStateRepository gameStateRepository,
            DBExecutor dbExecutor
    ) {
        return new GameService(
                boardRepository,
                gameRoomRepository,
                gameStateRepository,
                dbExecutor
        );
    }
}