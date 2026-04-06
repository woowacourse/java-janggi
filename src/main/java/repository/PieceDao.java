package repository;

import entity.PieceEntity;

import java.util.List;

public interface PieceDao {

    void saveAll(List<PieceEntity> pieceEntities);
}
