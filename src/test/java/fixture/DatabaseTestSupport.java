package fixture;

import database.SchemaInitializer;
import database.context.ConnectionContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseTestSupport {

    @BeforeAll
    static void setSchemaSQL() {
        SchemaInitializer schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();
    }

    @BeforeEach
    void startTransaction() throws SQLException {
        ConnectionContext.setConnection();
        Connection connection = ConnectionContext.getConnection();
        connection.setAutoCommit(false);
    }

    @AfterEach
    void rollbackTransaction() throws SQLException {
        Connection connection = ConnectionContext.getConnection();
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            connection.close();
        }
        ConnectionContext.clear();
    }

}
