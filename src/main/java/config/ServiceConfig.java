package config;

import infra.DBExecutor;
import repository.BoardRepository;
import repository.GameRoomRepository;
import service.GameService;

public class ServiceConfig {

    public GameService gameService(
            BoardRepository boardRepository,
            GameRoomRepository gameRoomRepository,
            DBExecutor dbExecutor
    ) {
        return new GameService(
                boardRepository,
                gameRoomRepository,
                dbExecutor
        );
    }
}
