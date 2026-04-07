package model.config;

import config.DatabaseConfig;
import config.DatabaseInitializer;
import org.junit.jupiter.api.Test;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseInitializeTest {
    @Test
    void 게임과_기물_테이블을_생성할_수_있다() throws SQLException {
        DatabaseInitializer.initialize();
        Connection connection = DatabaseConfig.getConnection();

        assertThat(existsTable(connection, "GAME")).isTrue();
        assertThat(existsTable(connection,"PIECE")).isTrue();
    }

    private boolean existsTable(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        boolean exists = resultSet.next();
        resultSet.close();
        return exists;
    }
}
