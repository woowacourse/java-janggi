package janggi.dao;

import janggi.entity.BoardEntity;
import java.util.Optional;

public interface BoardDao {

    void save(BoardEntity boardEntity);

    Optional<Long> findByJanggiId(long janggiId);
}
