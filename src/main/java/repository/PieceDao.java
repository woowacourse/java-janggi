package repository;

import entity.PieceEntity;

import java.util.List;

public interface PieceDao {

    void saveAll(List<PieceEntity> pieceEntities);

    void deleteByPosition(Long gameId, int row, int col);

    void updatePosition(Long gameId, int fromRow, int fromCol, int toRow, int toCol);
}
