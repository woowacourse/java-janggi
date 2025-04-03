package janggi.dao;

import janggi.entity.PieceEntity;
import java.util.List;
import java.util.Optional;

public interface PieceDao {
    void save(PieceEntity pieceEntity);

    Optional<PieceEntity> findByBoardIdAndRowAndColumn(long boardId, int row, int column);

    Optional<PieceEntity> findByPieceId(long pieceId);

    List<PieceEntity> findAllByBoardIdAndIsAlive(long boardId, boolean isAlive);
}
