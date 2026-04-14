package janggi.db.dao;

import janggi.db.entity.PieceEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PieceDao {

    List<PieceEntity> selectPiecesByGameId(Connection connection, int gameId) throws SQLException;

    void insertNewPieces(Connection connection, List<PieceEntity> pieceEntities) throws SQLException;

    void deletePiecesByGameId(Connection connection, int gameId) throws SQLException;
}
