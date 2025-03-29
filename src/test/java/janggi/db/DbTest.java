package janggi.db;

import org.junit.jupiter.api.BeforeEach;

public abstract class DbTest {

    private final MockConnection mockConnection = new MockConnection();
    private final DBInitializer dbInitializer = new DBInitializer(mockConnection);

    @BeforeEach
    void setUp() {
        dbInitializer.init();
    }

    protected MockConnection mockConnection() {
        return mockConnection;
    }
}
