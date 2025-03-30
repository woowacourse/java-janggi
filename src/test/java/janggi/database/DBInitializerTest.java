package janggi.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBInitializerTest {

    private static DBConnector dbConnector;
    private static DBInitializer dbInitializer;

    @BeforeAll
    static void setUp() {
        //given
        dbConnector = new TestDBConnector();
        dbInitializer = new DBInitializer(dbConnector);
        dbInitializer.createTables();
    }

    @DisplayName("TestDBConnector 연결 확인")
    @Test
    void test1() {
        //when & then
        Assertions.assertDoesNotThrow(() -> dbConnector.getConnection());
    }

    @DisplayName("테이블 생성 확인")
    @Test
    void test2() {
        //given
        String query = "SHOW TABLES";

        Assertions.assertDoesNotThrow(() -> {
            try {
                Connection connection = dbConnector.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                Set<String> tables = new HashSet<>();
                while (resultSet.next()) {
                    tables.add(resultSet.getString(1));
                }
                Assertions.assertTrue(tables.containsAll(Set.of("team", "pieces", "turn")));
            } catch (SQLException e) {
                Assertions.fail("[ERROR] 테스트 테이블 생성 확인 중 에러 발생했습니다.");
            }
        });
    }

}