package janggi.database.dao;

import janggi.database.QueryProcessor;
import janggi.database.entity.PieceEntity;
import java.util.List;

public class PieceDao {

    public Long add(final String pieceType, final String team, final int x, final int y) {
        final String query = "INSERT INTO piece (piece_type, team, x, y) VALUES (?, ?, ?, ?)";
        return QueryProcessor.executeInsert(query, pieceType, team, x, y);
    }

    public void delete(final int x, final int y) {
        final String query = "DELETE FROM piece WHERE x = ? AND y = ?";
        QueryProcessor.executeUpdate(query, x, y);
    }

    public void deleteAll() {
        QueryProcessor.executeUpdate("DELETE FROM piece");
    }

    public List<PieceEntity> findAll() {
        final String query = "SELECT * FROM piece";
        return QueryProcessor.executeQueryList(query, resultSet -> new PieceEntity(
                resultSet.getLong("id"),
                resultSet.getString("piece_type"),
                resultSet.getString("team"),
                resultSet.getInt("x"),
                resultSet.getInt("y")
        ));
    }
}
