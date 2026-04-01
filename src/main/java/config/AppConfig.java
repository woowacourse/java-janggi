package config;

import java.sql.Connection;
import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;
import repository.impl.BoardRepositoryImpl;
import repository.impl.GameRoomRepositoryImpl;
import repository.impl.GameStateRepositoryImpl;
import service.GameService;

public class AppConfig {

    public Connection connectionManager() {
        return H2ConnectionManager.getConnection();
    }

    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl(connectionManager());
    }

    public GameRoomRepository gameRoomRepository() {
        return new GameRoomRepositoryImpl(connectionManager());
    }

    public GameStateRepository gameStateRepository() {
        return new GameStateRepositoryImpl(connectionManager());
    }

    public GameService boardService() {
        return new GameService(boardRepository(), gameRoomRepository(), gameStateRepository(), connectionManager());
    }

}
