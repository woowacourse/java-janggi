package db;

import static org.assertj.core.api.Assertions.assertThat;

import dao.JanggiDao;
import java.sql.SQLException;
import model.janggiboard.JanggiBoard;
import model.janggiboard.JanggiBoardSetUp;
import org.junit.jupiter.api.Test;

public class JanggiDBTest {

    JanggiDao janggiDao = new JanggiDao();

    @Test
    public void connection() throws SQLException {
        try (final var connection = janggiDao.getConnection()) {
            assertThat(connection).isNotNull();
        }
    }

    @Test
    public void settingNewJanggiBoardTest() {
        JanggiBoard janggiBoard = new JanggiBoard(JanggiBoardSetUp.INNER_SANG);
        janggiDao.settingNewJanggiBoard(janggiBoard.getJanggiBoard());
    }
}
