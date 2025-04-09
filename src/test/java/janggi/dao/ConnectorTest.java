package janggi.dao;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class ConnectorTest {

    @Test
    void connectTestDBTest() {
        H2DatabaseConnector connector = new H2DatabaseConnector();

        assertThatCode(connector::getConnection).doesNotThrowAnyException();
    }

    @Test
    void connectProductDBTest() {
        MySQLDatabaseConnector connector = new MySQLDatabaseConnector();

        assertThatCode(connector::getConnection).doesNotThrowAnyException();
    }
}
