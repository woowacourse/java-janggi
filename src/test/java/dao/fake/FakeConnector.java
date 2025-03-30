package dao.fake;

import dao.Connector;
import java.sql.Connection;
import java.sql.SQLException;

public class FakeConnector implements Connector {
    private final InMemoryDatabase database;

    public FakeConnector(InMemoryDatabase database) {
        this.database = database;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new InMemoryConnection(database);
    }
}
