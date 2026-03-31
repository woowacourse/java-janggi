package domain.board.Service;

import domain.board.repository.BoardRepositoryImpl;

public class BoardService {

    private final BoardRepositoryImpl boardRepositoryImpl;

    public BoardService(BoardRepositoryImpl boardRepositoryImpl) {
        this.boardRepositoryImpl = boardRepositoryImpl;
    }
}
