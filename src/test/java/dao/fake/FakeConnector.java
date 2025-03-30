package dao.fake;

import dao.Connector;
import java.sql.Connection;

public final class FakeConnector implements Connector {
    private final InMemoryDatabase database;

    public FakeConnector(InMemoryDatabase database) {
        this.database = database;
    }

    @Override
    public Connection getConnection() {
        return new InMemoryConnection(database);
    }
}
