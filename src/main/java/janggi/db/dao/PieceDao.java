package janggi.db.dao;

import janggi.db.entity.PieceEntity;

import java.util.List;

public interface PieceDao {
    void saveAll(Long gameId, List<PieceEntity> pieces);

    List<PieceEntity> findByGameId(Long gameId);

    void deleteByPosition(Long gameId, int x, int y);

    void updatePosition(Long gameId, int fromX, int fromY, int toX, int toY);
}
