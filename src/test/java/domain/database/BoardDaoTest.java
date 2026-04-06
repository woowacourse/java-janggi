package domain.database;

import database.SchemaInitializer;
import database.connection.DBConnector;
import database.dao.BoardDao;
import database.dao.JdbcBoardDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class BoardDaoTest {

    private SchemaInitializer schemaInitializer;
    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        schemaInitializer = new SchemaInitializer();
        schemaInitializer.readShemaSQLFile();
        boardDao = new JdbcBoardDao();
    }

    @Test
    @DisplayName("Board를 저장하면 AutoIncrement에 의한 ID를 반환한다.")
    void saveBoardReturnAutoIncrementId() throws SQLException{
        try (Connection connection = DBConnector.getConnection()) {

            Long savedId = boardDao.save(connection);

            Assertions.assertThat(savedId)
                    .isNotNull()
                    .isGreaterThan(0L);
        }
    }

    @Test
    @DisplayName("Board를 저장할 때마다 ID는 다르다.")
    void shouldDifferentBoardIdWheneverSaveBoard() throws SQLException{
        try (Connection connection = DBConnector.getConnection()) {
            Long savedId1 = boardDao.save(connection);
            Long savedId2 = boardDao.save(connection);

            Assertions.assertThat(savedId1)
                    .isNotEqualTo(savedId2);
        }
    }
}
