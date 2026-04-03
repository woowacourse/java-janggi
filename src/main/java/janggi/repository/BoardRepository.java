package janggi.repository;

import janggi.entity.BoardEntity;
import java.util.Optional;

public interface BoardRepository {

    long save(BoardEntity boardEntity);

    boolean existsById(long id);

    Optional<BoardEntity> findById(long id);

    boolean deleteById(long id);
}
