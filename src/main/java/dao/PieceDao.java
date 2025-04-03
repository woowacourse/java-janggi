package dao;

import domain.position.JanggiPosition;
import entity.PieceEntity;

import java.util.List;
import java.util.Optional;

public interface PieceDao {
    List<PieceEntity> findAll();

    Optional<PieceEntity> findByPosition(JanggiPosition position);

    void deleteByPosition(JanggiPosition position);

    void deleteAll();

    void save(PieceEntity pieceEntity);
}
