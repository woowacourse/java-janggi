package janggi.database.dao;

import janggi.database.QueryProcessor;
import janggi.database.entity.TurnEntity;

public class TurnDao {

    public Long add(final String turn) {
        final String query = "INSERT INTO turn (team) VALUES (?)";
        return QueryProcessor.executeInsert(query, turn);
    }

    public TurnEntity find() {
        final String query = "SELECT * FROM turn";
        return QueryProcessor.executeQuery(query, resultSet -> new TurnEntity(
                resultSet.getLong("id"),
                resultSet.getString("team")
        ));
    }

    public void update(final String turn) {
        final String query = "UPDATE turn SET team = ?";
        QueryProcessor.executeUpdate(query, turn);
    }

    public void delete() {
        final String query = "DELETE FROM turn";
        QueryProcessor.executeUpdate(query);
    }
}
