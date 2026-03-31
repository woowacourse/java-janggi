package service;

import repository.impl.BoardRepositoryImpl;

public class BoardService {

    private final BoardRepositoryImpl boardRepositoryImpl;

    public BoardService(BoardRepositoryImpl boardRepositoryImpl) {
        this.boardRepositoryImpl = boardRepositoryImpl;
    }
}
