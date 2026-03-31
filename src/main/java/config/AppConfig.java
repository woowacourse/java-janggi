package config;

import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;
import repository.impl.BoardRepositoryImpl;
import repository.impl.GameRoomRepositoryImpl;
import repository.impl.GameStateRepositoryImpl;
import service.GameService;

public class AppConfig {

    public H2ConnectionManager connectionManager() {
        return new H2ConnectionManager();
    }

    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl();
    }

    public GameRoomRepository gameRoomRepository() {
        return new GameRoomRepositoryImpl();
    }

    public GameStateRepository gameStateRepository() { return new GameStateRepositoryImpl();}

    public GameService boardService() {
        return new GameService(boardRepository(), gameRoomRepository(), gameStateRepository(), connectionManager());
    }
}
