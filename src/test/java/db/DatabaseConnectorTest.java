package db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseConnectorTest {

    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;

    DatabaseConnector databaseConnector;

    @BeforeEach
    void setUp() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        databaseConnector = new DatabaseConnector(mockDataSource);
    }

    @Test
    void getConnection_shouldReturnMockConnection() throws SQLException {
        Connection connection = databaseConnector.getConnection();
        assertThat(connection).isNotNull();
        verify(mockDataSource).getConnection();
    }
}
