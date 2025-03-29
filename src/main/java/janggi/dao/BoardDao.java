package janggi.dao;

import janggi.domain.Position;
import janggi.entity.BoardEntity;
import java.util.List;
import java.util.Optional;

public interface BoardDao {
    void save(BoardEntity boardEntity, Position departure);

    Optional<BoardEntity> findByJanggiIdAndRowAndColumn(long janggiId, int row, int column);

    List<BoardEntity> findAllByJanggiIdAndIsAlive(long janggiId, boolean isAlive);
}
