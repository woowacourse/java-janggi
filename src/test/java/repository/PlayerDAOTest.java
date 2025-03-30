package repository;

import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.mock.TestConnector;

public final class PlayerDAOTest {
    private static final Connector CONNECTOR = new TestConnector();
    private static final PlayerDAO PLAYER_REPOSITORY = new PlayerDAO(CONNECTOR);

    @Test
    @DisplayName("Player 추가를 요청한다.")
    void test_create() throws SQLException {
    }

    @Test
    @DisplayName("Player의 값을 수정한다.")
    void test_update() throws SQLException {
    }

    @Test
    @DisplayName("같은 보드에 속한 플레이어 정보를 요청한다.")
    void test_findAllByGameId() throws SQLException {
    }
}
