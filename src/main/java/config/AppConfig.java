package config;

import domain.board.Service.BoardService;
import domain.board.repository.BoardRepositoryImpl;

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
