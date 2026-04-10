package janggi.service;

import janggi.dao.BoardDao;
import janggi.dao.entity.BoardEntity;
import janggi.domain.board.Board;

public class BoardService {
    private final BoardDao boardDao;

    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public void save(Board board) {
        boardDao.save(BoardEntity.fromDomain(board));
    }

    public Board findByGameId(int gameId) {
        return boardDao.getByGameId(gameId).toDomain();
    }
}
