package config;

import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;
import repository.impl.BoardRepositoryImpl;
import repository.impl.GameRoomRepositoryImpl;
import repository.impl.GameStateRepositoryImpl;

public class RepositoryConfig {

    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl();
    }

    public GameStateRepository gameStateRepository() {
        return new GameStateRepositoryImpl();
    }

    public GameRoomRepository gameRoomRepository() {
        return new GameRoomRepositoryImpl();
    }
}
