package janggi.database.dao;

import janggi.database.QueryProcessor;
import janggi.database.entity.PieceEntity;
import java.util.List;

public class PieceDao {

    private final QueryProcessor queryProcessor;

    public PieceDao(final QueryProcessor queryProcessor) {
        this.queryProcessor = queryProcessor;
    }

    public Long add(final String pieceType, final String team, final int x, final int y) {
        final String query = "INSERT INTO piece (piece_type, team, x, y) VALUES (?, ?, ?, ?)";
        return queryProcessor.executeInsert(query, pieceType, team, x, y);
    }

    public void delete(final int x, final int y) {
        final String query = "DELETE FROM piece WHERE x = ? AND y = ?";
        queryProcessor.executeUpdate(query, x, y);
    }

    public void deleteAll() {
        queryProcessor.executeUpdate("DELETE FROM piece");
    }

    public List<PieceEntity> findAll() {
        final String query = "SELECT * FROM piece";
        return queryProcessor.executeQueryList(query, resultSet -> new PieceEntity(
                resultSet.getLong("id"),
                resultSet.getString("piece_type"),
                resultSet.getString("team"),
                resultSet.getInt("x"),
                resultSet.getInt("y")
        ));
    }

    public boolean exist() {
        final String query = "SELECT 1 FROM piece LIMIT 1";
        return queryProcessor.executeQuery(query, resultSet -> true) != null;
    }
}
