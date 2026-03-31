package config;

import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;
import repository.impl.BoardRepositoryImpl;
import repository.impl.GameRoomRepositoryImpl;
import repository.impl.GameStateRepositoryImpl;
import service.BoardService;

public class AppConfig {

    public H2ConnectionManager connectionManager() {
        return new H2ConnectionManager();
    }

    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl(connectionManager());
    }

    public GameRoomRepository gameRoomRepository() {
        return new GameRoomRepositoryImpl(connectionManager());
    }

    public GameStateRepository gameStateRepository() { return new GameStateRepositoryImpl(connectionManager());}

    public BoardService boardService() {
        return new BoardService(boardRepository(), gameRoomRepository(), gameStateRepository());
    }
}
