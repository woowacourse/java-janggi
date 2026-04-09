package domain.database;

import database.SchemaInitializer;
import database.connection.DBConnector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class DatabaseTest {

    private SchemaInitializer schemaInitializer;

    @BeforeEach
    void setUp() {
        schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();
    }

    @Test
    @DisplayName("테스트에서 사용하는 데이터 베이스는 H2이다.")
    void h2DatabaseConnectionTest() throws Exception {
        // given
        try (Connection connection = DBConnector.getConnection()) {

            // when
            String expected = connection.getMetaData()
                    .getDatabaseProductName();

            // then
            Assertions.assertThat(expected)
                    .isEqualTo("H2");
        }
    }

    @Test
    @DisplayName("테스트 DB에는 Board, Intersection 테이블이 생성되어야한다.")
    void h2DatabaseTableTest() throws SQLException {
        try (Connection connection = DBConnector.getConnection();
             Statement stmt = connection.createStatement()) {

            ResultSet resultSet = stmt.executeQuery("show tables");

            List<String> tableNames = new ArrayList<>();
            while (resultSet.next()) {
                tableNames.add(resultSet.getString(1).toLowerCase());
            }

            Assertions.assertThat(tableNames)
                    .contains("board", "intersection");
        }
    }

}
