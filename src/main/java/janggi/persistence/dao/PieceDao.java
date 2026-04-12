package janggi.persistence.dao;

import janggi.persistence.entity.PieceEntity;

import java.sql.Connection;
import java.util.List;

public interface PieceDao {
    void createAll(Connection conn, List<PieceEntity> entities);

    List<PieceEntity> findByGameId(String gameId);

    void deleteByGameId(Connection conn, String gameId);

    void updateAll(Connection conn, String gameId, List<PieceEntity> entities);
}
