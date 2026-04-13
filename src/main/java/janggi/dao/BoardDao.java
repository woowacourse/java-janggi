package janggi.dao;

import janggi.dao.entity.BoardEntity;
import janggi.dao.entity.MoveEntity;
import java.util.List;

public interface BoardDao {
    // Create
    void save(int gameId, MoveEntity move);

    void save(BoardEntity boardEntity);

    // Read
    List<MoveEntity> findAllByGameId(int gameId);

    void delete(int gameId, int x, int y);
}
