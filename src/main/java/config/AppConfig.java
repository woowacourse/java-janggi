package config;

import service.BoardService;
import repository.impl.BoardRepositoryImpl;

public class AppConfig {

    public H2ConnectionManager connectionManager(){
        return new H2ConnectionManager();
    }

    public BoardRepositoryImpl boardRepository(){
        return new BoardRepositoryImpl(connectionManager());
    }

    public BoardService boardService(){
        return new BoardService(boardRepository());
    }
}
