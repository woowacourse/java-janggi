package janggi.dao;

import janggi.entity.BoardEntity;
import java.util.List;
import java.util.Optional;

public interface BoardDao {
    void save(BoardEntity boardEntity);

    Optional<BoardEntity> findByJanggiIdAndRowAndColumn(long janggiId, int row, int column);

    Optional<BoardEntity> findByBoardId(long boardId);

    List<BoardEntity> findAllByJanggiIdAndIsAlive(long janggiId, boolean isAlive);
}
