package janggi.repository;

import janggi.entity.BoardCellEntity;

public interface BoardCellRepository {

    long save(BoardCellEntity boardCellEntity);

    BoardCellEntity findById(long id);

}
