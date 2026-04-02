package janggi.repository;

import janggi.entity.BoardEntity;

public interface BoardRepository {

    long save(BoardEntity boardEntity);

    BoardEntity findById(long id);
}
