package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class PieceDaoTest {

    private final TestPieceDao testPieceDao = new TestPieceDao();

    @Test
    public void connection() throws SQLException {
        try (Connection connection = testPieceDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }
}
