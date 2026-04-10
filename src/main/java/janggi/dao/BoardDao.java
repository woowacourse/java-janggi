package janggi.dao;

import janggi.dao.entity.BoardEntity;

public interface BoardDao {
    void save(BoardEntity boardEntity);

    BoardEntity getByGameId(int gameId);
}
