package entity;

import dao.BoardDao;
import java.util.List;

public class BoardRepository {
    private final BoardDao boardDao;

    public BoardRepository(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public List<BoardEntity> getAllBoards() {
        return boardDao.getBoardEntities();
    }
}
