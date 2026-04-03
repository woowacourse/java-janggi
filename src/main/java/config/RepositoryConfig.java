package config;

import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.impl.BoardRepositoryImpl;
import repository.impl.GameRoomRepositoryImpl;

public class RepositoryConfig {

    public BoardRepository boardRepository() {
        return new BoardRepositoryImpl();
    }

    public GameRoomRepository gameRoomRepository() {
        return new GameRoomRepositoryImpl();
    }
}
