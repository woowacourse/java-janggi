package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameDaoTest {

    private static final String TEST_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

    private GameDao gameDao;
    private DBConnection dbConnection;

    @BeforeEach
    void setUp() {
        gameDao = new GameDao();
        dbConnection = new H2DBConnection(TEST_URL);

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM piece");
            statement.execute("DELETE FROM game");
            statement.execute("ALTER TABLE game ALTER COLUMN id RESTART WITH 1");
        } catch (Exception e) {
            throw new RuntimeException("테스트 DB 초기화 실패", e);
        }
    }

    @Test
    void 새로운_게임을_저장하면_ID_1번을_반환한다() {
        try (Connection connection = dbConnection.getConnection()) {
            long gameId = gameDao.create(connection, "CHO");
            assertThat(gameId).isEqualTo(1L);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 게임의_턴을_성공적으로_업데이트한다() throws Exception {
        try (Connection connection = dbConnection.getConnection()) {
            long gameId = gameDao.create(connection, "CHO");
            gameDao.updateTurn(connection, gameId, "HAN");
            String currentTurn = gameDao.findTurn(connection, gameId);
            assertThat(currentTurn).isEqualTo("HAN");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 존재하지_않는_방_번호를_조회하면_예외가_발생한다() throws Exception {
        try (Connection connection = dbConnection.getConnection()) {
            assertThatThrownBy(() -> gameDao.findTurn(connection, 999L))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 게임 방입니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 저장된_게임_목록을_최신순으로_조회한다() throws Exception {
        try (Connection connection = dbConnection.getConnection()) {
            gameDao.create(connection, "CHO"); // 1번 방
            gameDao.create(connection, "HAN"); // 2번 방

            Map<Long, String> games = gameDao.findAll(connection);

            assertThat(games).hasSize(2);
            assertThat(games.keySet()).containsExactly(2L, 1L);
            assertThat(games.values()).containsExactly("HAN", "CHO");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
