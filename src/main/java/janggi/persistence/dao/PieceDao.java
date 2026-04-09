package janggi.persistence.dao;

import janggi.persistence.entity.PieceEntity;

import java.util.List;

public interface PieceDao {
    void createAll(List<PieceEntity> entities);

    List<PieceEntity> findByGameId(String gameId);

    void deleteByGameId(String gameId);

    void updateAll(String gameId, List<PieceEntity> entities);
}
