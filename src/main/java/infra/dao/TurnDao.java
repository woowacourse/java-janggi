package infra.dao;

import infra.entity.TurnEntity;
import utils.DBUtils;

public class TurnDao {

    public TurnEntity save(final TurnEntity turnEntity) {
        final String query = "INSERT INTO turn (team) VALUES (?)";
        final Long id = DBUtils.executeInsert(query, turnEntity.getTeam());

        return new TurnEntity(id, turnEntity.getTeam());
    }

    public boolean exists() {
        final String query = "SELECT EXISTS (SELECT 1 FROM turn LIMIT 1)";

        return Boolean.TRUE.equals(
            DBUtils.executeQuery(query, resultSet -> resultSet.getBoolean(1)));
    }

    public TurnEntity findLast() {
        final String query = "SELECT id, team FROM turn ORDER BY id DESC LIMIT 1";

        return DBUtils.executeQuery(query, resultSet ->
            new TurnEntity(
                resultSet.getLong("id"),
                resultSet.getString("team")
            )
        );
    }

    public void deleteAll() {
        DBUtils.executeUpdate("DELETE FROM turn");
    }
}
